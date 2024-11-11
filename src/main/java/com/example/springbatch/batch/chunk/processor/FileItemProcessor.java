package com.example.springbatch.batch.chunk.processor;


import com.example.springbatch.batch.domain.Product;
import com.example.springbatch.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {


    @Override
    public Object processItem(Object item) throws Exception {
        return null;
    }
}
