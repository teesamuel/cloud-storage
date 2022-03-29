package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialByUser(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialById(Integer credentialId);


    @Select("SELECT * FROM CREDENTIALS")
    Credential getAllCredential();

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES( #{url},#{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Delete("Delete FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredential(Integer credentialId);

    @Update(" UPDATE CREDENTIALS SET url = #{url} , username = #{username}, password = #{password},  key = #{key} WHERE credentialId = #{credentialId}")
    int updateCredential(String url, String username, String password, String key, Integer credentialId);

//    support
//    application - Migration (issue)
//    config for Threshold
//    Application (fixing)
}


