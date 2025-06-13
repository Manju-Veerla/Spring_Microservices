package com.example.user.model.enums;

import lombok.Getter;

@Getter
public enum AddressType {
	PERSONAL(100), OFFICIAL(200) , OTHER(300);
	

AddressType(int code){
    this.code = code;
  }

  private int code;

}