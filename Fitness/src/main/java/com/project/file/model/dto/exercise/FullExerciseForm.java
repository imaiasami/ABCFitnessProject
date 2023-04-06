package com.project.file.model.dto.exercise;

import lombok.Data;

@Data
public class FullExerciseForm {
	private long ex_no;
	private String musc_id;
	private String equip_id;
	private int diff;
	private int freq;
	private String image;
	private String name_ko;
	private String effect_ko;
	private String guide_ko;
	private String name_jp;
	private String effect_jp;
	private String guide_jp;
	private String name_en;
	private String effect_en;
	private String guide_en;
	
	public void toBrTag() {
		if (this.effect_ko != null) this.effect_ko = this.effect_ko.replace("\n", "<br>");
		if (this.guide_ko != null) this.guide_ko = this.guide_ko.replace("\n", "<br>");
		if (this.effect_jp != null) this.effect_jp = this.effect_jp.replace("\n", "<br>");
		if (this.guide_jp != null) this.guide_jp = this.guide_jp.replace("\n", "<br>");
		if (this.effect_en != null) this.effect_en = this.effect_en.replace("\n", "<br>");
		if (this.guide_en != null) this.guide_en = this.guide_en.replace("\n", "<br>");
	}
}
