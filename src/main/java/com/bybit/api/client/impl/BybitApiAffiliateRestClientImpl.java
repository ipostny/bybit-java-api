package com.bybit.api.client.impl;

import com.bybit.api.client.domain.affiliate.AffiliateDataRequest;
import com.bybit.api.client.domain.preupgrade.PreUpgradeDataRequest;
import com.bybit.api.client.domain.user.UserDataRequest;
import com.bybit.api.client.domain.user.request.UserSubMemberRequest;
import com.bybit.api.client.restApi.BybitApiAffiliateRestClient;
import com.bybit.api.client.restApi.BybitApiService;
import com.bybit.api.client.restApi.BybitApiUserRestClient;
import com.bybit.api.client.service.BybitJsonConverter;

import static com.bybit.api.client.constant.Helper.listToString;
import static com.bybit.api.client.service.BybitApiServiceGenerator.createService;
import static com.bybit.api.client.service.BybitApiServiceGenerator.executeSync;

/**
 * Implementation of Bybit's REST API using Retrofit with synchronous/blocking
 * method calls.
 */
public class BybitApiAffiliateRestClientImpl implements BybitApiAffiliateRestClient {
    private final BybitApiService bybitApiService;
    private final BybitJsonConverter converter = new BybitJsonConverter();

    public BybitApiAffiliateRestClientImpl(String apiKey, String secret, String baseUrl, boolean debugMode, long recvWindow, String logOption) {
        bybitApiService = createService(BybitApiService.class, apiKey, secret, baseUrl, debugMode, recvWindow, logOption, "", false);
    }

    public BybitApiAffiliateRestClientImpl(String apiKey, String secret, String baseUrl, boolean debugMode, long recvWindow, String logOption, boolean demoTrading) {
        bybitApiService = createService(BybitApiService.class, apiKey, secret, baseUrl, debugMode, recvWindow, logOption, "", demoTrading);
    }


    @Override
    public Object affUserList(AffiliateDataRequest affiliateDataRequest) {
        return executeSync(bybitApiService.affUserList(
                affiliateDataRequest.getCursor(),
                affiliateDataRequest.getSize(),
                affiliateDataRequest.getNeedDeposit(),
                affiliateDataRequest.getNeed30(),
                affiliateDataRequest.getNeed365()
        ));

    }

    @Override
    public Object affCustomerInfo(AffiliateDataRequest affiliateDataRequest) {
        if (affiliateDataRequest.getUid() != null) {
            return executeSync(bybitApiService.affCustomerInfo(
                    affiliateDataRequest.getUid()
            ));
        }
        return null;
    }
}
