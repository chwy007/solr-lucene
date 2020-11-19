package com.yujizi;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: ychw
 * @Description:
 * @Date: 2020/11/18 17:56
 */
public class SolrJManager {


    @Test
    public void test() throws IOException, SolrServerException {
        //增与update
        String url="http://localhost:8983/solr/account";
        SolrClient httpSolrClient = new HttpSolrClient.Builder(url).build();
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id","111");
        document.addField("name","莫问收获");
        httpSolrClient.add(document);
        httpSolrClient.commit();


    }


    @Test
    public void test1() throws IOException, SolrServerException {
//      solr删
        String url="http://localhost:8983/solr/account";
        SolrClient httpSolrClient = new HttpSolrClient.Builder(url).build();
        httpSolrClient.deleteByQuery("name:向问天",1000);


    }

    @Test
    public void test2() throws IOException, SolrServerException {
//      solr查询
        String url="http://localhost:8983/solr/account";
        SolrClient sc = new HttpSolrClient.Builder(url).build();

        SolrQuery params=new SolrQuery();
        params.set("q","accountname:网银行号");
//        params.set("q","score:{1 TO 100]");
        params.addSort("myscore", SolrQuery.ORDER.desc);
        params.setStart(0);
        params.setRows(10);
        params.setHighlight(true);
        params.setHighlightSimplePre("<em style='color:red'>");
        params.setHighlightSimplePost("</em>");
        params.addHighlightField("accountname");

        QueryResponse response = sc.query(params);
        SolrDocumentList results = response.getResults();
        System.out.println("总数"+results.getNumFound());
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
//            System.out.println(result.get("score"));
            System.out.println(result.get("accountname"));
            System.out.println(result.get("myscore"));
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            Map<String, List<String>> map = highlighting.get(result.get("id"));
            List<String> list = map.get("accountname");
            System.out.println(list.get(0));
            System.out.println("------------------------------");


        }

    }

}
