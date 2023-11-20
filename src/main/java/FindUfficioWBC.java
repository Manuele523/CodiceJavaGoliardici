import ObjectForTest.Superpratica.RwaWbc;
import ObjectForTest.Superpratica.Superpratica;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static Constants.Constants.statiDaAssegnare;
import static Utils.AddressingFileRule.*;
import static Utils.PreprareCommonScript.*;
import static Utils.PreprareCommonScript.TypeTable.T_PAD_FIDO;
import static java.util.Objects.nonNull;

public class FindUfficioWBC {

    /*******************************************************************************************************************
    * <p> FIND OFFICE FROM FILE IN 2005/3005                                                                      </p> *
    *                                                                                                                  *
    * <p> numIncident           -> insert incident number                                                         </p> *
    * <p> SUPER_PRATICA.json    -> Insert into /resources/SUPER_PRATICA.json your practice.json took from mongoDb </p> *
    *                                                                                                                  *
    *******************************************************************************************************************/
    @Test
    public void findOffice() throws IOException {
        String numIncident = "INSERISCI_NUMERO_INCIDENT";
        BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/SUPER_PRATICA.json"), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Superpratica superpratica = mapper.readValue(reader, Superpratica.class);

        Map<String, String> mapOffice = new HashMap<>();
        if (nonNull(superpratica) && statiDaAssegnare.contains(superpratica.getFiltri().getUltimoStato())) {

            addrNot01025(superpratica, mapOffice);

            addrFactoring(superpratica, mapOffice);
            addrProattivo(superpratica, mapOffice);
            addrFilialiEstere(superpratica, mapOffice);
            addrFilialiVirtuali(superpratica, mapOffice);
            addrRealEstate(superpratica, mapOffice);
            addrPlafond(superpratica, mapOffice);
            addrFinanzaStrutturata(superpratica, mapOffice);
            addrNpl(superpratica, mapOffice);
            addrBonis(superpratica, mapOffice);

            addrDefault(superpratica, mapOffice);

            for (Map.Entry<?, ?> office : mapOffice.entrySet()) {
                RwaWbc rwaWbc = superpratica.getRwaWbc();
                System.out.println("L'ufficio corretto per la pratica è " + office.getValue() +
                        " con instradamento " + office.getKey() +
                        "\n----------------------------------------------------------------------" +
                        "\nFilialeAttuale: " + superpratica.getFilialeIsp() +
                        "\nIter: " + (nonNull(rwaWbc) ? rwaWbc.getCodITER() : null) +
                        "\nClasseCompetenzaDeliberativa: " + (nonNull(rwaWbc) ? rwaWbc.getCodClasseCompetenzaDeliberativa() : null) +
                        "\nIndustryRichiedente: " + (nonNull(rwaWbc) ? rwaWbc.getCodIndustryRichiedente() : null) +
                        "\nPuntoOperativoPratica: " + superpratica.getPuntoOperativoPratica());

                if (!StringUtils.equalsIgnoreCase(superpratica.getFilialeIsp(), String.valueOf(office.getValue()))) {
                    createScriptMongoloDb(superpratica.get_id(), String.valueOf(office.getValue()));
                    createFileMongolo(numIncident);
                }
            }
        } else if (!statiDaAssegnare.contains(superpratica.getFiltri().getUltimoStato())) {
            System.out.println("Pratica non in stato da_assegnare!!");
        }
    }
}
