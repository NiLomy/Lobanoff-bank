package ru.kpfu.itis.lobanov.data.services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.data.entities.BankInfo;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.BankInfoRepository;
import ru.kpfu.itis.lobanov.data.services.BankInfoService;
import ru.kpfu.itis.lobanov.dtos.BankInfoDto;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankInfoServiceImpl implements BankInfoService {
    private final BankInfoRepository bankInfoRepository;
    private final Mapper<BankInfo, BankInfoDto> bankInfoMapper;

    @Override
    public Map<String, BankInfoDto> getAll() {
        return bankInfoRepository.findAll().stream().collect(Collectors.toMap(BankInfo::getBin, bankInfoMapper::toResponse));
    }

    @Override
    public void uploadInfo() {
        try (FileReader reader = new FileReader("impl/src/main/resources/static/russia.json")) {
            JsonArray json = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement element : json.asList()) {
                JsonObject data = element.getAsJsonObject();
                bankInfoRepository.save(
                        BankInfo.builder()
                                .bin(data.get("bin").getAsString())
                                .brand(data.get("brand").getAsString())
                                .type(data.get("type").getAsString())
                                .category(data.get("category").getAsString())
                                .issuer(data.get("issuer").getAsString())
                                .alpha2(data.get("alpha_2").getAsString())
                                .alpha3(data.get("alpha_3").getAsString())
                                .country(data.get("country").getAsString())
                                .build()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
