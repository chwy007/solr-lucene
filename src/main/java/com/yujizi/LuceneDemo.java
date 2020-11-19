package com.yujizi;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


/**
 *
 * @Author: ychw
 * @Description:
 * @Date: 2020/11/16 20:08
 */
public class LuceneDemo {
//创建索引
    @Test
    public void test01() throws IOException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());
        Analyzer analyzer=new IKAnalyzer();
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);



//        创建indexWriter对象
        IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);

//        创建field对象
        File file=new File("D:\\temp\\aaa");
        File[] files = file.listFiles();
        for (File f : files) {
//          创建document对象
            Document document=new Document();
            String name = f.getName();
            Field fileName=new TextField("fileName",name, Field.Store.YES);
            long l = FileUtils.sizeOf(f);
            System.out.println("文件size"+l);
            Field fileSize=new LongPoint("fileSize",l);
            String path = f.getPath();
            Field filePath=new StoredField("filePath",path);
            String s = FileUtils.readFileToString(f);
            Field fileContent=new TextField("fileContent",s, Field.Store.YES);
            document.add(fileName);
            document.add(fileSize);
            document.add(filePath);
            document.add(fileContent);
            indexWriter.addDocument(document);

        }

//        关流
        indexWriter.close();
        


    }

//    搜索索引
    @Test
    public void test02() throws IOException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());

        IndexReader indexReader= DirectoryReader.open(directory);
//      创建检索工具
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
//      根据term，进行倒排索引
        Query query=new TermQuery(new Term("fileContent","高富帅"));
        TopDocs topDocs = indexSearcher.search(query, 4);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("fileName"));
            System.out.println(document.get("fileSize"));
            System.out.println(document.get("filePath"));
            System.out.println(document.get("fileContent"));
            System.out.println("===========================================");

        }
        indexReader.close();


    }

    @Test
    public void test03() throws IOException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());

        IndexReader indexReader= DirectoryReader.open(directory);
//      创建检索工具
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
//      根据term，进行倒排索引
        Query query=new MatchAllDocsQuery();
        TopDocs topDocs = indexSearcher.search(query, 20);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("fileName"));
            System.out.println(document.get("fileSize"));
            System.out.println(document.get("filePath"));
//            System.out.println(document.get("fileContent"));
            System.out.println("===========================================");

        }
        indexReader.close();


    }


    @Test
    public void test04() throws IOException, ParseException {
        Directory directory= FSDirectory.open(new File("D:\\temp\\index").toPath());

        IndexReader indexReader= DirectoryReader.open(directory);
//      创建检索工具
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
//      根据term，进行倒排索引
        QueryParser queryParser=new QueryParser("fileName",new IKAnalyzer());
        Query query = queryParser.parse("fileName:quartz is dic fileName:maven");


        TopDocs topDocs = indexSearcher.search(query, 20);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("fileName"));
            System.out.println(document.get("fileSize"));
            System.out.println(document.get("filePath"));
//            System.out.println(document.get("fileContent"));
            System.out.println("===========================================");

        }
        indexReader.close();


    }

    @Test
    public void test05(){
        System.out.println(Math.ceil(1.1));
        System.out.println(Math.floor(1.9));

    }


}
