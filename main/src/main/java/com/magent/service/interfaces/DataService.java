package com.magent.service.interfaces;


import com.magent.domain.dto.UpdateDataDto;
import com.magent.utils.ariphmeticbeans.ComissionCalculatorImpl;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;


public interface DataService {

    UpdateDataDto getData(Long userId, Long syncId);

    UpdateDataDto updateData(UpdateDataDto dataDto) throws ComissionCalculatorImpl.FormulaNotFound, NotFoundException, ParseException;

    String saveFile(String fileName, String control, MultipartFile file) throws IOException;

}
