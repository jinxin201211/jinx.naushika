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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
                List<File> children_dir = Arrays.stream(base_dir.listFiles()).filter(p -> p.isDirectory()).collect(Collectors.toList());
                DirectoryModel[] children_model = new DirectoryModel[children_dir.size()];
                for (int i = 0; i < children_dir.size(); i++) {
                    children_model[i] = new DirectoryModel();
                    children_model[i].Name = children_dir.get(i).getName();
                    children_model[i].Seq = i;

                    if (null != children_dir.get(i).listFiles()) {
                        List<File> files_dir = Arrays.stream(children_dir.get(i).listFiles()).filter(p -> p.isFile()).collect(Collectors.toList());
                        DirectoryModel[] file_model = new DirectoryModel[files_dir.size()];
                        for (int j = 0; j < files_dir.size(); j++) {
                            if (files_dir.get(j).isFile()) {
                                file_model[j] = new DirectoryModel();
                                file_model[j].Name = files_dir.get(j).getName();
                                file_model[j].Seq = j;
                            }
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
