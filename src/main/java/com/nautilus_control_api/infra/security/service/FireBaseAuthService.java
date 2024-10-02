package com.nautilus_control_api.infra.security.service;

import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public interface FireBaseAuthService {
    /**
     * Decodifica o token do Firebase e retorna o FirebaseToken
     * @param token token do Firebase
     * @return FirebaseToken
     */
    FirebaseToken decodeUIDToken(String token);

    /**
     * Retorna o usuário do Firebase pelo UID
     * @param firebaseUID UID do Firebase
     * @return UserRecord
     */
    UserRecord getUserByFirebaseUID(String firebaseUID);

    /**
     * Retorna o UID do Firebase pelo token
     * @param token token do Firebase
     * @return UID do Firebase
     */
    String getFirebaseUID(String token);

    /**
     * Cria um usuário no Firebase
     * @param email email do usuário
     * @param password senha do usuário
     * @return UserRecord
     */
    UserRecord createUser(String email, String password);

    /**
     * Retorna o UID do Firebase pelo email
     * @param email email do usuário
     * @return UID do Firebase
     */
    String getFirebaseUIDByEmail(String email);

    /**
     * Deleta um usuário do Firebase
     * @param uid UID do Firebase
     */
    void deleteFirebaseUser(String uid);
}
