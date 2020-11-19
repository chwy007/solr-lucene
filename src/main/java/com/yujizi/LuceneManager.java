package com.yujizi;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @Author: ychw
 * @Description:
 * @Date: 2020/11/17 15:24
 */
public class LuceneManager {
//  删除索引
    @Test
    public void test01() throws IOException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());
        Analyzer analyzer=new IKAnalyzer();
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);



//        创建indexWriter对象
        IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);

        indexWriter.deleteAll();
        indexWriter.close();
    }

    //  删除索引
    @Test
    public void test02() throws IOException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());
        Analyzer analyzer=new IKAnalyzer();
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);



//        创建indexWriter对象
        IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);
//        Query query=new TermQuery(new Term("fileContent","有"));
//        indexWriter.deleteDocuments(query);
        indexWriter.deleteAll();
        indexWriter.close();
    }



    //  修改索引
    @Test
    public void test03() throws IOException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());
        Analyzer analyzer=new IKAnalyzer();
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);



//        创建indexWriter对象
        IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);
        Document doc=new Document();
        doc.add(new TextField("fileName","test.txt", Field.Store.YES));
        doc.add(new TextField("filePath","D:\\temp\\aaa\\test.txt", Field.Store.YES));
        indexWriter.updateDocument(new Term("fileName","插件"),doc);
        indexWriter.close();
    }









}
