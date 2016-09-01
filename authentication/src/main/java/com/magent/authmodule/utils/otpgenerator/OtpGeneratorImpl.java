package com.magent.authmodule.utils.otpgenerator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

/**
 * Created by artomov.ihor on 14.06.2016.
 */
@Service
public class OtpGeneratorImpl implements OtpGenerator {

    @SuppressFBWarnings("PREDICTABLE_RANDOM")
    public String generate() {
        int min = 10000000;
        int max = 99999999;
        return String.valueOf(min + (int) (Math.random() * (max - min)));
    }
}
