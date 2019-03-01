package by.ladyka.club.endpoints.api;

import by.ladyka.club.service.files.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/file")
public class FilesController {
    private final StorageService storageService;

    @Autowired
    public FilesController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public @ResponseBody
    ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {
        Map<String, Object> responce = new HashMap<>();
        try {
            String filePath = storageService.store(file);
            responce.put("filePath", filePath);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
            return new ResponseEntity<>(responce, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}