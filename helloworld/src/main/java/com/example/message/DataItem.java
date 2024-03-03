package com.example.message;

import lombok.Data;

import java.util.List;

@Data
public class DataItem{
	private String cluster;
	private List<ColumnsItem> columns;
	private String dbTrafficId;
	private int rows;
	private String sql;
	private String errorMsg;
}
