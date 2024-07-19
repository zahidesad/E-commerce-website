package com.service;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import com.tc.ObjectFactory;
import com.tc.TCKimlikNoDogrula;
import com.tc.TCKimlikNoDogrulaResponse;


@Service
public class TCNumberVerificationService {

    private WebServiceTemplate webServiceTemplate;

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public boolean verify(long tcNumber, String name, String surname, int birthYear) {
        try {
            ObjectFactory factory = new ObjectFactory();
            TCKimlikNoDogrula request = factory.createTCKimlikNoDogrula();
            request.setTCKimlikNo(tcNumber);
            request.setAd(name);
            request.setSoyad(surname);
            request.setDogumYili(birthYear);

            TCKimlikNoDogrulaResponse response = (TCKimlikNoDogrulaResponse) webServiceTemplate
                    .marshalSendAndReceive(request);

            return response.isTCKimlikNoDogrulaResult();
        } catch (Exception e) {
            return false;
        }
    }
}
