//===----------------------------------------------------------------------===//
//
//                         JA
//
// server.java
//
// Identification: serverc/server.java
//
// Usage: Server Process, listen to the port, while there is a 
// Socket request, create a thread serverthr, and start
//
// Last Modified : 2021.12.31 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package encrypt;

import javax.crypto.*;
import javax.crypto.spec.*;

public class GenerateKey {

    static public SecretKey genkey(String key) {
	try {
	    byte[] keyB = (key + "             ").substring(0, 8).getBytes();
	    DESKeySpec spec = new DESKeySpec(keyB);
	    
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");

	    SecretKey secret = factory.generateSecret(spec);
	    return secret;
	} catch (Exception e) {
	    return null;
	}
    }
}
