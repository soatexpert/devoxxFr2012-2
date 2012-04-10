/*
 * Copyright (c) 2012 Aurélien VIALE
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.soat.devoxx.game.controllers;

import java.io.InputStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import fr.soat.devoxx.game.exceptions.QuestionImportingError;
import fr.soat.devoxx.game.forms.UploadItem;
import fr.soat.devoxx.game.services.QuestionServices;
import fr.soat.devoxx.game.services.QuestionsImporter;
import fr.soat.devoxx.game.tools.TilesUtil;

/**
 * @author aurelien
 * 
 */
@Controller
@RequestMapping(value = "/admin/question")
public class AdminQuestionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminQuestionController.class);

    // @Autowired
    // AdminQuestionService adminQuestionService;
    
    @Autowired
    QuestionServices questionServices;

    @RequestMapping(value = "/")
    public String showAllQuestions() {
        String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;

        // TODO get all questions

        return forward;
    }

    @RequestMapping(value = "/{questionId}")
    public String showUser(@PathVariable Integer questionId, Model model) {
        // String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
        // try {
        // forward = TilesUtil.DFR_ADMIN_SHOWQUESTION_PAGE;
        // //TODO a supp
        // throw new QuestionServiceException();
        // } catch (QuestionServiceException e) {
        // model.addAttribute("error", "admin.error.question.get");
        // model.addAttribute("errorParams", questionId);
        // LOGGER.info("Error while fetching question", e);
        // }
        // return forward;
        return "";
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importQuestionsForm(Model model) {
        model.addAttribute("importQuestions", new UploadItem());
        return TilesUtil.DFR_ADMIN_IMPORTQUESTIONS_PAGE;
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importQuestionsFromFile(@ModelAttribute("importQuestions") @Valid UploadItem uploadItem, 
            BindingResult result, Model model) {
        String forward = TilesUtil.DFR_ADMIN_IMPORTQUESTIONS_PAGE;

        if (result.hasErrors()) {
            return forward;
        }

        try {
            MultipartFile file = uploadItem.getFileData();
            if (file.getSize() > 0) {
                if (file.getSize() > 3276800) { // 25Mo max
                    result.reject("admin.error.question.uploadsize.empty", new Object[] { file.getSize() }, "Uploaded file size is too big");
                    LOGGER.info("Uploaded file size is too big : " + file.getSize());
                    return forward;
                }
                
                InputStream inputStream = file.getInputStream();                

                QuestionsImporter importer = new QuestionsImporter(inputStream, 0);
                importer.setQuestionServices(questionServices);
                importer.importQuestions();
                
                inputStream.close();
                
                forward = "redirect:/admin/question/import?success=true";
            }
            else {
                LOGGER.info("Uploaded file size is empty !");
                result.reject("admin.error.question.uploadsize.empty");
            }
        } catch (QuestionImportingError e) {
            result.reject("admin.error.question.import");
            LOGGER.info("Error while importing question from file", e);
        } catch (Exception e) {
            result.reject("admin.error.question.upload");
            LOGGER.info("Error while uploading question from file", e);
        }

        return forward;
    }
}
