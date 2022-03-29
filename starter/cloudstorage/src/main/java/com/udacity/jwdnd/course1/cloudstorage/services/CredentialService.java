package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper  credentialMapper;
    public EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        return credentialMapper.insert(credential);
    }

    public  int deleteRecordById(Integer credentialId){
        return  credentialMapper.deleteCredential(credentialId);
    }

    public int updateCredential(Credential credential, Integer credentialId) {
        return credentialMapper.updateCredential(credential.getUrl(),credential.getUsername(),this.encryptionService.encryptValue(credential.getPassword(),credential.getKey()),credential.getKey(), credentialId);
    }


    public boolean isUserCredentialsAvailable(Integer userId) {
        return credentialMapper.getCredentialByUser(userId) == null;
    }
    public List<Credential> getCredentialByUser(Integer userId) {
        return credentialMapper.getCredentialByUser(userId);
    }
    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }
}
