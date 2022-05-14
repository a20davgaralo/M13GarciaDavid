package com.dga.springboot.m13garciadavid.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Author: David García Alonso
 *  Versió: 1.0
 *  Interfície que determina els mètodes que ha d'implementar la classe que la implementi
 *  Gestiona les accions que podem fer amb els arxius que pujem al nostre client
 */
public interface IUploadFileService {

    public Resource load(String filename) throws MalformedURLException;

    public String copy(MultipartFile file) throws IOException;

    public boolean delete(String filename);

    public void deleteAll();

    public void init() throws IOException;
}
