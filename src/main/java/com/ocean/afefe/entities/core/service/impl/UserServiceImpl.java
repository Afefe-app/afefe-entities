package com.ocean.afefe.entities.core.service.impl;

import com.ocean.afefe.entities.common.Status;
import com.ocean.afefe.entities.core.service.UserService;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserProfile;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRepository;
import com.ocean.afefe.entities.modules.auth.repository.UserProfileRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import com.tensorpoint.toolkit.tpointcore.commons.TimeZone;
import com.tensorpoint.toolkit.tpointcore.date.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final OrgMemberRepository orgMemberRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile getOrCreateBasicUserProfile(User user){
        UserProfile userProfile = userProfileRepository.findFirstByUser(user);
        if(Objects.isNull(userProfile)){
            userProfile = UserProfile.builder()
                    .user(user)
                    .displayName(user.getFullName())
                    .timeZone(TimeZone.UTC)
                    .build();
            userProfile = userProfileRepository.saveAndFlush(userProfile);
        }
        return userProfile;
    }

    @Override
    public OrgMember registerUserInOrganization(User user, Organization organization){
        OrgMember orgMember = OrgMember.builder()
                .organization(organization)
                .user(user)
                .invitationStatus(Status.PENDING)
                .invitedAt(DateUtils.getServerUTCNowInstant())
                .build();
        return orgMemberRepository.saveAndFlush(orgMember);
    }

    @Override
    public User validateUserExistenceByEmailAndOrganization(String emailAddress, Organization organization){
        OrgMember orgMember = orgMemberRepository.findFirstByUser_EmailAddressAndOrganization(emailAddress, organization);
        if(Objects.isNull(orgMember)){
            throw HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, "User record not found in current organization");
        }
        return orgMember.getUser();
    }
}
