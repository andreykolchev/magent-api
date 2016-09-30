package com.magent.servicemodule.service.interfaces;


import com.magent.domain.dto.UpdateDataDto;
import com.magent.servicemodule.utils.ariphmeticbeans.ComissionCalculatorImpl;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;


public interface DataService {

    UpdateDataDto getData(Long userId, Long syncId);

    UpdateDataDto updateData(UpdateDataDto dataDto) throws ComissionCalculatorImpl.FormulaNotFound, NotFoundException, ParseException;

    String saveFile(String fileName, MultipartFile file) throws IOException;

}
