package com.richmond.cookbook.controller;

import com.richmond.cookbook.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BucketController {
    @Autowired
    private AmazonClient amazonClient;

    @PostMapping(path="/storage/uploadRecipeImage")
    public String uploadRecipeImage(@RequestParam(value="id", required=false) String id, @RequestPart(value="image") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            if (id==null) {
                return "redirect:/recipe/edit";
            }
            return "redirect:/recipe/edit?id=" + id;
        }

        String fileName = this.amazonClient.uploadFile(file);

        attributes.addFlashAttribute("message", fileName);

        if (id==null) {
            return "redirect:/recipe/edit";
        }
        return "redirect:/recipe/edit?id=" + id;
    }

    @DeleteMapping("/deleteFile")
    public boolean deleteFile(@RequestPart(value="url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
