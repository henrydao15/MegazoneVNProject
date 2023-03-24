package com.megazone.core.feign.crm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crm
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BO", description = "")
@ToString
public class CrmSearchBO extends PageEntity {

	@ApiModelProperty(value = "")
	private String search;

	@ApiModelProperty(value = "ID")
	private Integer poolId;

	@ApiModelProperty(value = "ID")
	private Integer sceneId;
	@ApiModelProperty(value = "type")
	private Integer label;
	@ApiModelProperty(value = "")
	private String sortField;
	@ApiModelProperty(value = " 1  2 ")
	private Integer order;
	@ApiModelProperty(value = "")
	private List<Search> searchList = new ArrayList<>();

	@ApiModel(value = "")
	public enum FieldSearchEnum {
		/**
		 *
		 */
		IS_NULL(5),
		/**
		 *
		 */
		IS_NOT_NULL(6),
		/**
		 *
		 */
		DATE_TIME(12),
		/**
		 *
		 */
		DATE(11),
		/**
		 *
		 */
		CONTAINS(3),
		/**
		 *
		 */
		IS(1),
		/**
		 *
		 */
		IS_NOT(2),
		/**
		 *
		 */
		NOT_CONTAINS(4),
		/**
		 *
		 */
		GT(7),
		/**
		 *
		 */
		EGT(8),
		/**
		 *
		 */
		LT(9),
		/**
		 *
		 */
		ELT(10),
		/**
		 *
		 */
		ID(11),
		/**
		 *
		 */
		NULL(0),


		SCRIPT(-1);

		private Integer type;

		FieldSearchEnum(Integer type) {
			this.type = type;
		}

		@JsonCreator
		public static FieldSearchEnum create(String type) {
			for (FieldSearchEnum searchEnum : values()) {
				if (searchEnum.getType().toString().equals(type)) {
					return searchEnum;
				}
			}
			return NULL;
		}

		public Integer getType() {
			return type;
		}

		@Override
		public String toString() {
			return type.toString() + ":" + name();
		}

		public String valueOf(Integer type) {
			return type.toString() + ":" + name();
		}
	}

	@Data
	@ApiModel(value = "")
	@Accessors(chain = true)
	public static class Search {
		@ApiModelProperty(value = "")
		private String name;
		@ApiModelProperty(value = "")
		private String formType;

		@ApiModelProperty(value = "")
		@JsonProperty("type")
		private FieldSearchEnum searchEnum;

		@ApiModelProperty(value = "")
		private List<String> values = new ArrayList<>();

		public Search(String name, String formType, FieldSearchEnum searchEnum, List<String> values) {
			this.name = name;
			this.formType = formType;
			this.searchEnum = searchEnum;
			this.values = values;
		}

		public Search() {
		}
	}
}
