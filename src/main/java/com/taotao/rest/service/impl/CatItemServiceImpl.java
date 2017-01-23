package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.CatNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JediusClientSingle;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.CatItemService;
import com.taotao.util.JsonUtils;

@Service
public class CatItemServiceImpl implements CatItemService {

	@Value("${MAX_CAT_ITEM_COUNTER}")
	private int MAX_CAT_ITEM_COUNTER;

	/**
	 * ItemCateogry Hashtable name
	 */
	@Value("${INDEX_CATEGORY_REDIS_KEY}")
	private String INDEX_CATEGORY_REDIS_KEY;

	@Autowired
	private JediusClientSingle jediusClientSingle;

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		CatResult result = new CatResult();
		result.setData(getItemCatListByParentId(0));
		return result;
	}

	/**
	 * 查询分类列表
	 * 
	 * @return
	 */
	private List<?> getItemCatListByParentId(long parentId) {
		List<Object> resultList = new ArrayList<>();

		// 查询是否有已知答案在redius系统里面
		try {
			String resultFromRedius = jediusClientSingle.get(String.valueOf(parentId));
			if (!StringUtils.isBlank(resultFromRedius)) {
				List<TbItemCat> jsonToList = JsonUtils.jsonToList(resultFromRedius, TbItemCat.class);
				return jsonToList;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 设置COUNTER统计类别数目
		int counter = 0;
		// 把HTML代码加入在数据当中以json格式返回
		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
					counter++;
				} else {
					catNode.setName(tbItemCat.getName());
				}
				// 如果类别太多，只输出最大类别数目
				if (counter > MAX_CAT_ITEM_COUNTER) {
					break;
				}
				catNode.setUri("/product/" + tbItemCat.getId() + ".html");
				catNode.setItem(getItemCatListByParentId(tbItemCat.getId()));
				resultList.add(catNode);
			} else {
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}

		// 添加已查找到的资料到redius缓存服务器提升加载速度
		try {
			String listDataToJsonString = JsonUtils.objectToJson(resultList);
			jediusClientSingle.hset(INDEX_CATEGORY_REDIS_KEY, String.valueOf(parentId), listDataToJsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 返回值list
		return resultList;
	}

}
