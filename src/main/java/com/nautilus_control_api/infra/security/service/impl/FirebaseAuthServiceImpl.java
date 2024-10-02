package com.nautilus_control_api.infra.security.service.impl;

import com.google.firebase.auth.*;
import com.nautilus_control_api.infra.security.service.FireBaseAuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
public class FirebaseAuthServiceImpl implements FireBaseAuthService {
    public FirebaseToken decodeUIDToken(String token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Exception {}", e.getLocalizedMessage());
        }
        return null;
    }

    public UserRecord getUserByFirebaseUID(String firebaseUID) {
        try {
            return FirebaseAuth.getInstance().getUser(firebaseUID);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Exception {}", e.getLocalizedMessage());
        }
        return null;
    }

    public String getFirebaseUID(String token) {
        var decodedToken = decodeUIDToken(token);
        return decodedToken != null ? decodedToken.getUid() : null;
    }

    public UserRecord createUser(String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);
        UserRecord newuser;
        try {
            newuser = FirebaseAuth.getInstance().createUser(request);
        } catch (NullPointerException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro na requisição, verifique os dados.");

        } catch (FirebaseAuthException e) {

            if(e.getAuthErrorCode().equals(AuthErrorCode.EMAIL_ALREADY_EXISTS)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao criar usuário.");
        }
        return newuser;
    }

    public String getFirebaseUIDByEmail(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return userRecord != null ? userRecord.getUid() : null;
        } catch (FirebaseAuthException e) {
            log.error("Firebase Exception {}", e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não registrado ou credenciais inválidas!", e);
        }
    }

    public void deleteFirebaseUser(String uid) {
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Exception {}", e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao deletar usuário.");
        }
    }
}
