package com.ocean.afefe.entities.core.security.otp;

import com.ocean.afefe.entities.common.Status;
import com.ocean.afefe.entities.common.SystemParamKeys;
import com.ocean.afefe.entities.core.localstore.LocalParamStorage;
import com.ocean.afefe.entities.modules.auth.models.UserOtpAction;
import com.ocean.afefe.entities.modules.auth.models.UserOtpAuth;
import com.ocean.afefe.entities.modules.auth.repository.UserOtpAuthRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import com.tensorpoint.toolkit.tpointcore.crypto.CryptoUtilities;
import com.tensorpoint.toolkit.tpointcore.date.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpValidationServiceImpl implements OtpValidationService{

    private final LocalParamStorage localParamStorage;
    private final UserOtpAuthRepository otpAuthRepository;


    @Override
    public void validateOtp(String rawOtp, String emailAddress, UserOtpAction action){
        UserOtpAuth otpAuth = otpAuthRepository.findTopByEmailAddressAndActionOrderByCreatedAtDesc(emailAddress, action);
        try {
            if (Objects.isNull(otpAuth)) {
                throw HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, "Invalid otp");
            }
            SystemParamKeys otpExpKey = SystemParamKeys.USER_OTP_EXPIRATION_IN_MIN;
            String expTimeString = localParamStorage.getParamValueOrDefault(otpExpKey, otpExpKey.getDefaultValue());
            long expLong = Long.parseLong(expTimeString);
            Instant now = DateUtils.getServerUTCNowInstant();
            Instant currentOtpTime = otpAuth.getCreatedAt().plus(expLong, ChronoUnit.MINUTES);
            if (currentOtpTime.isBefore(now)) {
                throw HttpUtil.getResolvedException(ResponseCode.OTP_INVALID, "This otp is no longer valid");
            }
            String hashedOtp = CryptoUtilities.base36Encode(rawOtp);
            if (!hashedOtp.equals(otpAuth.getOtpHash())) {
                throw HttpUtil.getResolvedException(ResponseCode.OTP_INVALID, "This otp is not valid");
            }
            otpAuth.setStatus(Status.APPROVED);
            otpAuthRepository.saveAndFlush(otpAuth);
        }catch (Exception e){
            if(Objects.nonNull(otpAuth)){
                otpAuth.setStatus(Status.REJECTED);
            }
            throw HttpUtil.getResolvedException(e);
        }
    }
}
