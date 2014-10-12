/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.core9.editor.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModelProperty;


@XmlRootElement(name = "Block")
public class Block {
	private long id;
	private Category category;
	private String name;
	private List<String> photoUrls = new ArrayList<String>();
	private List<Tag> tags = new ArrayList<Tag>();
	private String status;
	private String data;

	@XmlElement(name = "id")
	@ApiModelProperty(value = "id of block", required = true)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlElement(name = "category")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "data")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@XmlElementWrapper(name = "photoUrls")
	@XmlElement(name = "photoUrl")
	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	@XmlElementWrapper(name = "tags")
	@XmlElement(name = "tag")
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@XmlElement(name = "status")
	@ApiModelProperty(value = "block status in the store", allowableValues = "available,pending,sold")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
