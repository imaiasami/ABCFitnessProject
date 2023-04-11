package com.project.file.model.entity.exercise;

import lombok.Data;

@Data
public class Exercise {
	private long ex_no;
	private String musc_id;
	private String equip_id;
	private int diff;
	private int freq;
	private String image;
	private String muscle;
	private String equip;
	private String name;
	private String effect;
	private String guide;
	
	public void toBrTag() {
		if (this.effect != null) this.effect = this.effect.replace("\n", "<br>");
		if (this.guide != null) this.guide = this.guide.replace("\n", "<br>");
	}
}
