package com.service;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import tr.gov.nvi.tckimlik.ws.ObjectFactory;
import tr.gov.nvi.tckimlik.ws.TCKimlikNoDogrula;
import tr.gov.nvi.tckimlik.ws.TCKimlikNoDogrulaResponse;

import jakarta.xml.soap.MessageFactory;

@Service
public class TCNumberVerificationService {

    private final WebServiceTemplate webServiceTemplate;

    public TCNumberVerificationService() throws Exception {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
        messageFactory.afterPropertiesSet();

        webServiceTemplate = new WebServiceTemplate(messageFactory);

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.tc");
        marshaller.afterPropertiesSet();

        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setDefaultUri("http://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx");
        webServiceTemplate.afterPropertiesSet();
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
                    .marshalSendAndReceive("http://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx", request);

            return response.isTCKimlikNoDogrulaResult();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
