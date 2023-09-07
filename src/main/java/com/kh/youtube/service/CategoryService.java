package com.kh.youtube.service;


import com.kh.youtube.repo.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO dao;
}
