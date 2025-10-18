package com.tvseries.TvSeriesManagementSystemBackend.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SearchDto;
import com.tvseries.TvSeriesManagementSystemBackend.dto.SubmitDto;
import com.tvseries.TvSeriesManagementSystemBackend.entity.TvSeries;
import com.tvseries.TvSeriesManagementSystemBackend.entity.UserRequest;
import com.tvseries.TvSeriesManagementSystemBackend.entity.UserRequest;
import com.tvseries.TvSeriesManagementSystemBackend.repository.TvSeriesRepository;
import com.tvseries.TvSeriesManagementSystemBackend.repository.UserRequestRepository;
import com.tvseries.TvSeriesManagementSystemBackend.repository.UserRequestRepository;
import com.tvseries.TvSeriesManagementSystemBackend.service.TvSeriesService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TvSeriesServiceImpl implements TvSeriesService {

    @Autowired
    TvSeriesRepository repository;

    @Autowired
    UserRequestRepository requestRepository;

    @Autowired
    SmsService smsService;

    @Value("${file.upload-dir}")
    private String uploadDirPath;

    @Override
    public TvSeries add(SubmitDto dto) {
        log.info("Adding new TV series: {}", dto.getTitle());
        TvSeries series = new TvSeries();
        series.setTitle(dto.getTitle());
        series.setDescription(dto.getDescription());
        series.setCategory(dto.getCategory());
        series.setLanguage(dto.getLanguage());
        series.setQuality(dto.getQuality());
        series.setFormat(dto.getFormat());
        series.setReleasedDate(dto.getReleasedDate());
        series.setSeasons(dto.getSeasons());
        series.setEpisodes(dto.getEpisodes());
        series.setTrailer(dto.getTrailer());
        series.setImg(dto.getImg());
        series.setTags(dto.getTags());
        series.setImdb(dto.getImdb());
        series.setRo(dto.getRo());
        series.setStatus(dto.getStatus());
        series.setAddedDate(LocalDate.now().toString());
        series.setAddedBy("Admin");
        return repository.save(series);
    }

    @Override
    public List<TvSeries> get(SearchDto dto) {
        log.info("Searching TV series with filters: {}", dto);
        return repository.search(dto.getTitle(), dto.getCategory(), dto.getQuality(), dto.getReleasedDateFrom(),
                dto.getReleasedDateTo(), dto.getAddedDateFrom(), dto.getAddedDateTo());
    }

    @Override
    public Page<TvSeries> getAllSeries(Pageable pageable) {
        log.info("Fetching all TV series (paged) — Page: {}, Size: {}", pageable.getPageNumber(),
                pageable.getPageSize());
        return repository.findAll(pageable);
    }

    @Override
    public List<TvSeries> searchByQuery(String keyword) {
        log.info("Searching TV series by query: '{}'", keyword);
        String[] keywords = keyword.toLowerCase().split("\\s+");
        Set<TvSeries> result = new HashSet<>();
        for (String part : keywords) {
            List<TvSeries> found = repository.searchByQuery(part);
            result.addAll(found);
        }

        if (result.isEmpty()) {
            throw new EntityNotFoundException("Search Record Not Found");
        }

        return new ArrayList<>(result);
    }

    @Override
    public Page<TvSeries> searchByQueryPage(String keyword, Pageable pageable) {
        log.info("Searching (paged) TV series by query: '{}'", keyword);
        Page<TvSeries> result = repository.searchByQueryPage(keyword, pageable);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("Search Record Not Found");
        }
        return result;
    }

    @Override
    public TvSeries getById(Long id) {
        log.info("Fetching TV series by ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tv Series Not Found With Id" + id));
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting TV series by ID: {}", id);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tv Series Not Found With Id" + id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Some Error Occured");
        }
    }

    @Override
    public void update(Long id, SubmitDto dto) {
        log.info("Updating TV series with ID: {}", id);

        TvSeries existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tv Series Not Found With Id" + id));
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setLanguage(dto.getLanguage());
        existing.setQuality(dto.getQuality());
        existing.setFormat(dto.getFormat());
        existing.setReleasedDate(dto.getReleasedDate());
        existing.setImg(dto.getImg());
        existing.setTags(dto.getTags());
        existing.setImdb(dto.getImdb());
        existing.setRo(dto.getRo());
        existing.setStatus(dto.getStatus());
        existing.setAddedDate(LocalDate.now().toString());
        existing.setAddedBy("Admin");
        repository.save(existing);
    }

    @Override
    public List<TvSeries> latest10() {
        log.info("Fetching latest 10 TV series");
        List<TvSeries> result = repository.findTop10ByOrderByReleasedDateDesc();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Tv Series Not Found");
        }

        return result;

    }

    @Override
    public List<String> getAllYears() {
        log.info("Fetching all release years");
        return repository.findDistinctYears().stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<TvSeries> getTvSeriesByCategory(String category) {
        log.info("Fetching TV series by category: {}", category);
        List<TvSeries> result = repository.findByCategory(category);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Tv Series Not Found");
        }

        return result;
    }

    @Override
    public List<TvSeries> getTvSeriesByLanguage(String language) {
        log.info("Fetching TV series by language: {}", language);
        List<TvSeries> result = repository.findByLanguage(language);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Tv Series Not Found");
        }

        return result;
    }

    @Override
    public List<TvSeries> getTvSeriesByYear(int year) {
        log.info("Fetching TV series by release year: {}", year);
        List<TvSeries> result = repository.findByYear(year);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Tv Series Not Found");
        }

        return result;
    }

    @Override
    public List<TvSeries> getTvSeriesByCollection(String collection) {
        throw new UnsupportedOperationException("Unimplemented method 'getTvSeriesByCollection'");
        // List<TvSeries> result = repository.findByCollection(collection);
        // if (result.isEmpty()) {
        // throw new EntityNotFoundException("Tv Series Not Found");
        // }

        // return result;
    }

    @Override
    public void exportToCsv(HttpServletResponse response) {
        try {
            log.info("Starting export of TV series to CSV");
            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"list.csv\"");
            PrintWriter writer = response.getWriter();
            writer.println(
                    "Id,Title,Category,Language,Quality,Format,ReleasedDate,Description,Seasons,Episodes,AddedDate,AddedBy,IMDB,RottenTomatoes");
            List<TvSeries> list = getAllSeriesAsList();
            log.info("Fetched {} TV series to export", list.size());
            for (TvSeries tvSeries : list) {
                writer.println(String.format(Locale.ROOT, "%d,%s,%s,%s,%s,%s,%s,%s,%d,%d,%s,%s,%.1f,%d",
                        tvSeries.getId(),
                        escapeCsv(tvSeries.getTitle()),
                        escapeCsv(tvSeries.getCategory()),
                        escapeCsv(tvSeries.getLanguage()),
                        escapeCsv(tvSeries.getQuality()),
                        escapeCsv(tvSeries.getFormat()),
                        tvSeries.getReleasedDate(),
                        escapeCsv(tvSeries.getDescription()),
                        tvSeries.getSeasons(),
                        tvSeries.getEpisodes(),
                        tvSeries.getAddedDate(),
                        escapeCsv(tvSeries.getAddedBy()),
                        tvSeries.getImdb(),
                        tvSeries.getRo()));
            }
            writer.flush();
            log.info("TV series CSV export completed successfully");
        } catch (IOException ex) {
            log.error("CSV export failed due to I/O error: {}", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public List<TvSeries> getAllSeriesAsList() {
        log.info("Fetching all TV series (list)");
        return repository.findAll();
    }

    private String escapeCsv(String field) {
        if (field == null)
            return "";
        boolean mustQuote = field.contains(",") || field.contains("\"") || field.contains("\n");
        if (mustQuote) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    @Override
    public void exportToPdf(HttpServletResponse response) {
        try {

            log.info("Starting export of TV series to PDF");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=list.pdf");
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Font font = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Tv Series List", font);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Font contentFont = new Font(Font.HELVETICA, 12);
            List<TvSeries> list = getAllSeriesAsList();
            log.info("Fetched {} TV series to export", list.size());
            for (TvSeries tvSeries : list) {
                Paragraph paragraph = new Paragraph(
                        "Id: " + tvSeries.getId() +
                                "\nTitle: " + tvSeries.getTitle() +
                                "\nCategory: " + tvSeries.getCategory() +
                                "\nLanguage: " + tvSeries.getLanguage() +
                                "\nReleasedDate: " + tvSeries.getReleasedDate() +
                                "\nQuality: " + tvSeries.getQuality() +
                                "\nFormat: " + tvSeries.getFormat() +
                                "\nSeasons: " + tvSeries.getSeasons() +
                                "\nEpisodes: " + tvSeries.getEpisodes() +
                                "\nAddedDate: " + tvSeries.getAddedDate() +
                                "\nAddedBy: " + tvSeries.getAddedBy() +
                                "\nIMDB: " + tvSeries.getImdb() +
                                "\nRottenTomatoes: " + tvSeries.getRo() +
                                "\n",
                        contentFont);
                document.add(paragraph);
                document.add(new Paragraph("\n"));
            }

            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            table.addCell("Id");
            table.addCell("Title");
            table.addCell("Category");
            table.addCell("Language");
            table.addCell("Quality");
            table.addCell("Format");
            table.addCell("ReleasedDate");
            table.addCell("Seasons");
            table.addCell("Episodes");
            table.addCell("AddedDate");
            table.addCell("AddedBy");
            table.addCell("IMDB");
            table.addCell("RottenTomatoes");

            for (TvSeries tvSeries : list) {
                table.addCell(String.valueOf(tvSeries.getId()));
                table.addCell(tvSeries.getTitle());
                table.addCell(tvSeries.getCategory());
                table.addCell(tvSeries.getLanguage());
                table.addCell(tvSeries.getQuality());
                table.addCell(tvSeries.getFormat());
                table.addCell(String.valueOf(tvSeries.getReleasedDate()));
                table.addCell(String.valueOf(tvSeries.getSeasons()));
                table.addCell(String.valueOf(tvSeries.getEpisodes()));
                table.addCell(tvSeries.getAddedDate());
                table.addCell(tvSeries.getAddedBy());
                table.addCell(String.valueOf(tvSeries.getImdb()));
                table.addCell(String.valueOf(tvSeries.getRo()));
            }
            document.add(table);
            document.close();
            log.info("TV series PDF export completed successfully");
        } catch (IOException | DocumentException ex) {
            log.error("PDF export failed due to error: {}", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void exportToZip(HttpServletResponse response) {
        try {
            log.info("Starting ZIP export of TV series");
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=list.zip");
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            zipOutputStream.putNextEntry(new ZipEntry("Tv Series List.csv"));
            String contentCsv = getCsvContent();
            zipOutputStream.write(contentCsv.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            zipOutputStream.putNextEntry(new ZipEntry("Tv Series List.pdf"));
            byte[] contentPdf = getPdfContent();
            zipOutputStream.write(contentPdf);
            zipOutputStream.closeEntry();

            zipOutputStream.finish();
            log.info("ZIP export completed successfully");
        } catch (IOException | DocumentException ex) {
            log.error("ZIP export failed: {}", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getCsvContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "Id,Title,Category,Language,Quality,Format,ReleasedDate,Description,Seasons,Episodes,AddedDate,AddedBy,IMDB,RottenTomatoes\n");
        List<TvSeries> list = getAllSeriesAsList();
        log.info("Fetched {} TV series to export", list.size());
        for (TvSeries tvSeries : list) {
            sb.append(String.format(Locale.ROOT, "%d,%s,%s,%s,%s,%s,%s,%s,%d,%d,%s,%s,%.1f,%d\n",
                    tvSeries.getId(),
                    escapeCsv(tvSeries.getTitle()),
                    escapeCsv(tvSeries.getCategory()),
                    escapeCsv(tvSeries.getLanguage()),
                    escapeCsv(tvSeries.getQuality()),
                    escapeCsv(tvSeries.getFormat()),
                    tvSeries.getReleasedDate(),
                    escapeCsv(tvSeries.getDescription()),
                    tvSeries.getSeasons(),
                    tvSeries.getEpisodes(),
                    tvSeries.getAddedDate(),
                    escapeCsv(tvSeries.getAddedBy()),
                    tvSeries.getImdb(),
                    tvSeries.getRo()));
        }
        return sb.toString();

    }

    private byte[] getPdfContent() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Font font = new Font(Font.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("Tv Series List", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));
        Font contentFont = new Font(Font.HELVETICA, 12);
        List<TvSeries> list = getAllSeriesAsList();
        log.info("Fetched {} TV series to export", list.size());
        for (TvSeries tvSeries : list) {
            Paragraph paragraph = new Paragraph(
                    "Id: " + tvSeries.getId() +
                            "\nTitle: " + tvSeries.getTitle() +
                            "\nCategory: " + tvSeries.getCategory() +
                            "\nLanguage: " + tvSeries.getLanguage() +
                            "\nReleasedDate: " + tvSeries.getReleasedDate() +
                            "\nQuality: " + tvSeries.getQuality() +
                            "\nFormat: " + tvSeries.getFormat() +
                            "\nSeasons: " + tvSeries.getSeasons() +
                            "\nEpisodes: " + tvSeries.getEpisodes() +
                            "\nAddedDate: " + tvSeries.getAddedDate() +
                            "\nAddedBy: " + tvSeries.getAddedBy() +
                            "\nIMDB: " + tvSeries.getImdb() +
                            "\nRottenTomatoes: " + tvSeries.getRo() +
                            "\n",
                    contentFont);
            document.add(paragraph);
            document.add(new Paragraph("\n"));
        }
        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {

            File uploadDir = new File(uploadDirPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            List<String> allowedMimeTypes = Arrays.asList("video/mp4", "video/avi");
            String mimeType = file.getContentType();
            if (!allowedMimeTypes.contains(mimeType)) {
                throw new FileUploadException("Invalid file type");
            }

            String targetPath = uploadDirPath + File.separator + file.getOriginalFilename();
            File targetFile = new File(targetPath);
            file.transferTo(targetFile);

            return targetPath;

        } catch (IOException e) {
            throw new RuntimeException("Error Occured Uploading", e);
        }
    }

    @Override
    public void patch(Long id, SubmitDto dto) {
        log.info("Partially Updating TV series with ID : {}", id);

        TvSeries existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tv Series Not Found With Id" + id));
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setLanguage(dto.getLanguage());
        existing.setQuality(dto.getQuality());
        existing.setFormat(dto.getFormat());
        existing.setReleasedDate(dto.getReleasedDate());
        existing.setSeasons(dto.getSeasons());
        existing.setEpisodes(dto.getEpisodes());
        existing.setTrailer(dto.getTrailer());
        existing.setTags(dto.getTags());
        existing.setImdb(dto.getImdb());
        existing.setRo(dto.getRo());
        existing.setStatus(dto.getStatus());
        repository.save(existing);
    }

    @Override
    public void addRequest(String fname, String lname, String email, String contact, String content,
            MultipartFile file) {

        log.info("Adding user request: {}", fname);

        UserRequest userRequest = new UserRequest();
        userRequest.setFname(fname);
        userRequest.setLname(lname);
        userRequest.setEmail(email);
        userRequest.setContact(contact);
        userRequest.setContent(content);
        userRequest.setAddedDate(LocalDate.now().toString());

        if (file != null && !file.isEmpty()) {
            log.info("File uploaded: {}", file.getOriginalFilename());

            try {
                File uploadDir = new File(uploadDirPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                List<String> allowedMimeTypes = Arrays.asList(
                        "video/mp4",
                        "video/avi",
                        "video/x-msvideo",
                        "video/mpeg",
                        "image/jpeg",
                        "image/png",
                        "image/webp",
                        "text/plain",
                        "application/msword",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "application/vnd.ms-powerpoint",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation");
                String mimeType = file.getContentType();
                if (!allowedMimeTypes.contains(mimeType)) {
                    log.warn("File {} rejected due to invalid mime type: {}", file.getOriginalFilename(), mimeType);
                    throw new FileUploadException("Invalid file type");
                }

                String filePath = uploadDirPath + File.separator + file.getOriginalFilename();
                File targetFile = new File(filePath);
                file.transferTo(targetFile);
                userRequest.setFilePath(filePath);
                log.info("File successfully saved to {}", filePath);

            } catch (IOException e) {
                log.error("Error occurred while saving file", e);
                throw new RuntimeException("Error Occured Saving", e);
            }
        }

        requestRepository.save(userRequest);

        log.info("User request from {} saved successfully", fname);
        
        String message = "New user request from " + fname + " " + lname + " " + content;
        smsService.sendSms("+94 75 633 6141", message);

        log.info("Sent sms to admin {}", "+94 75 633 6141");
    }

}
