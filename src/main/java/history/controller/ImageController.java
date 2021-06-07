package history.controller;

import history.model.User;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class ImageController {

    private final UserService userService;

    @Autowired
    public ImageController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/file")
    public BufferedOutputStream getImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageFileName = "C://Projects/SemHistory/src/main/resources/images/";
        imageFileName += req.getParameter("type") + "/" + req.getParameter("src");
        String contentType = req.getServletContext().getMimeType(imageFileName);

        resp.setHeader("Content-Type", contentType);

        ServletOutputStream outStream;
        outStream = resp.getOutputStream();
        FileInputStream fin = new FileInputStream(imageFileName);

        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(outStream);
        int ch = 0;
        while ((ch = bin.read()) != -1)
            bout.write(ch);

        bin.close();
        fin.close();
        bout.close();
        outStream.close();
        return bout;
    }

    @RequestMapping(value = "/file/load", method = RequestMethod.POST)
    public void savePhoto(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) throws ServletException, IOException {
        String FILE_PATH_PREFIX = "C://Projects/SemHistory/src/main/resources/images/";
        String filename = file.getName();
        System.out.println(filename);
        String contentType = file.getContentType();
        if (contentType.contains("image")) {
            String type = req.getParameter("type") + "/";
            String id = req.getParameter("id") + "/";
            User user = userService.getById(Integer.parseInt(id));
            File check = new File(FILE_PATH_PREFIX + type + id);
            filename = "photo." + contentType.substring("image/".length()).toLowerCase();
            String lastPath = FILE_PATH_PREFIX + type + id + filename;
            if (check.exists()) {
                for (File myFile : check.listFiles())
                    if (myFile.isFile()) myFile.delete();
                file.transferTo(new File(lastPath));
            } else if (check.mkdirs()) {
                file.transferTo(new File(lastPath));
            }
            user.setPhoto(id + "/" + filename);
            userService.saveUser(user);
        }
    }
}