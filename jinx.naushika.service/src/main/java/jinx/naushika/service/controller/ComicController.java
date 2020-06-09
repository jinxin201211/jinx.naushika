package jinx.naushika.service.controller;

import jinx.naushika.service.Model.DirectoryModel;
import jinx.naushika.service.Model.Response;
import jinx.naushika.service.ServiceApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/comic")
public class ComicController {

    private static Logger logger = LoggerFactory.getLogger(ComicController.class);

    @RequestMapping("/test")
    public String test(@RequestParam Map map) {
        return "Hello World";
    }

    @Value("${spring.profiles.active}")
    private String SPRING_PROFILES_ACTIVE;
    @Value("${jinx.configure.base_dir}")
    private String BASE_DIR;

    @RequestMapping("/contents")
    public Response contents() throws Exception {
        File base_dir = new File(BASE_DIR);
        if (base_dir.exists()) {
            DirectoryModel model = new DirectoryModel();
            model.Name = base_dir.getName();

            if (null != base_dir.listFiles()) {
                File[] children_dir = base_dir.listFiles();
                DirectoryModel[] children_model = new DirectoryModel[children_dir.length];
                for (int i = 0; i < children_dir.length; i++) {
                    children_model[i] = new DirectoryModel();
                    children_model[i].Name = children_dir[i].getName();
                    children_model[i].Seq = i;

                    if (null != children_dir[i].listFiles()) {
                        File[] files_dir = children_dir[i].listFiles();
                        DirectoryModel[] file_model = new DirectoryModel[files_dir.length];
                        for (int j = 0; j < files_dir.length; j++) {
                            file_model[j] = new DirectoryModel();
                            file_model[j].Name = files_dir[j].getName();
                            file_model[j].Seq = j;
                        }
                        children_model[i].Children = file_model;
                    }
                }
                model.Children = children_model;
            }

            return new Response().success(model);
        } else {
            logger.error("目录不存在:" + BASE_DIR);
            return new Response().error("目录不存在:" + BASE_DIR);
        }
    }
}
