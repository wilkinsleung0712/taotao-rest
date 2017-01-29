package com.taotao.rest.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

    private String baseURL = "http://localhost:8983/solr";

    @Test
    public void addDocument() {

        try {
            // 创建一连接
            SolrServer solrServer = new HttpSolrServer(baseURL);
            // 创建一个文档对象
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", "552199");
            document.addField("item_title", "Gouda cheese wheel");
            document.addField("price", "49.99");
            // 把文档对象写入索引库
            solrServer.add(document);
            // 提交
            solrServer.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void deleteDocument() {
        try {
            // 创建一连接
            SolrServer solrServer = new HttpSolrServer(baseURL);
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getSolrDocumentListViaQuery() {

        try {
            SolrServer solrServer = new HttpSolrServer(baseURL);
            // 创建一个查询对象
            SolrQuery query = new SolrQuery();
            // 设置查询条件
            query.setQuery("*:*");
            query.setStart(20);
            query.setRows(50);
            // 执行查询
            QueryResponse response = solrServer.query(query);
            // 取查询结果
            SolrDocumentList documentList = response.getResults();
            System.out.println("Total records: " + documentList.getNumFound());
            for (SolrDocument solrDocument : documentList) {
                System.out.println(solrDocument.get("id"));
                System.out.println(solrDocument.get("item_title"));
                System.out.println(solrDocument.get("item_price"));
                System.out.println(solrDocument.get("item_image"));
            }
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
