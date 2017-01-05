package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.CatNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.CatItemService;

public class CatItemServiceImpl implements CatItemService {

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
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);

		// 返回值list
		List<String> resultList = new ArrayList<>();

		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUri("/product/" + tbItemCat.getId() + ".html");
				catNode.setItem(getItemCatListByParentId(tbItemCat.getId()));
			} else {
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}
