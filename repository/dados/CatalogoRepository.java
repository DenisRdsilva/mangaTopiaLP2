package repository.dados;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.catalogo.ItemCatalogo;
import models.catalogo.Manga;
// import models.catalogo.VolumeFisico;
// import models.enums.GeneroManga;
// import models.enums.TipoCapa;

public class CatalogoRepository {
    private static final String CAMINHO_JSON = "data/catalogo.json";
    private List<ItemCatalogo> itens;

    public CatalogoRepository() {
        this.itens = new ArrayList<>();
        carregarDoJson();
    }

    public List<ItemCatalogo> listarTodos() {
        return new ArrayList<>(itens);
    }

    public ItemCatalogo buscarPorId(String id) {
        for (ItemCatalogo item : itens) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public void salvar(ItemCatalogo item) {
        itens.add(item);
        salvarNoJson();
    }

    public void atualizar() {
        salvarNoJson();
    }

    private void carregarDoJson() {
        Path path = Paths.get(CAMINHO_JSON);
        if (!Files.exists(path)) {
            System.out.println("[Aviso] Arquivo " + CAMINHO_JSON + " não encontrado. Iniciando catálogo vazio.");
            return;
        }

        try {
            String conteudo = Files.readString(path, StandardCharsets.UTF_8);
            // Divide os blocos de objetos principais do array JSON
            String[] blocos = conteudo.split("\\},\\s*\\{\\s*\"idItem\"");

            for (int i = 0; i < blocos.length; i++) {
                String bloco = blocos[i];
                if (i > 0) bloco = "\"idItem\"" + bloco;

                String idItem = extrairString(bloco, "idItem");
                String idManga = extrairString(bloco, "id");
                String titulo = extrairString(bloco, "titulo");
                String autor = extrairString(bloco, "autor");
                // GeneroManga genero = GeneroManga.valueOf(extrairString(bloco, "genero"));
                int faixaEtaria = (int) extrairNumero(bloco, "faixaEtaria");

                int numeroVolume = (int) extrairNumero(bloco, "numeroVolume");
                double precoBase = extrairNumero(bloco, "precoBase");
                double pesoGramas = extrairNumero(bloco, "pesoGramas");
                String dimensoes = extrairString(bloco, "dimensoes");
                // TipoCapa tipoCapa = TipoCapa.valueOf(extrairString(bloco, "tipoCapa"));
                int estoque = (int) extrairNumero(bloco, "quantidadeEstoque");

                Manga manga = new Manga(idManga, titulo, autor, faixaEtaria);
                // VolumeFisico volume = new VolumeFisico(
                //         idItem, manga, precoBase, numeroVolume, pesoGramas, dimensoes, tipoCapa, estoque
                // );
                // this.itens.add(volume);
            }
        } catch (Exception e) {
            System.err.println("[Erro ao ler catalogo.json]: " + e.getMessage());
        }
    }

    public void salvarNoJson() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < itens.size(); i++) {
        //     if (itens.get(i) instanceof VolumeFisico vf) {
        //         Manga m = vf.getManga();
        //         json.append("  {\n");
        //         json.append(String.format("    \"idItem\": \"%s\",\n", vf.getId()));
        //         json.append("    \"manga\": {\n");
        //         json.append(String.format("      \"id\": \"%s\",\n", m.getId()));
        //         json.append(String.format("      \"titulo\": \"%s\",\n", m.getTitulo()));
        //         json.append(String.format("      \"autor\": \"%s\",\n", m.getAutor()));
        //         json.append(String.format("      \"genero\": \"%s\",\n", m.getGenero().name()));
        //         json.append(String.format("      \"faixaEtaria\": %d\n", m.getFaixaEtaria()));
        //         json.append("    },\n");
        //         json.append(String.format("    \"numeroVolume\": %d,\n", vf.getNumeroVolume()));
        //         json.append(String.format(java.util.Locale.US, "    \"precoBase\": %.2f,\n", vf.getPrecoBase()));
        //         json.append(String.format(java.util.Locale.US, "    \"pesoGramas\": %.1f,\n", vf.getPesoGramas()));
        //         json.append(String.format("    \"dimensoes\": \"%s\",\n", vf.getDimensoes()));
        //         json.append(String.format("    \"tipoCapa\": \"%s\",\n", vf.getTipoCapa().name()));
        //         json.append(String.format("    \"quantidadeEstoque\": %d\n", vf.getQuantidadeEstoque()));
        //         json.append("  }").append(i < itens.size() - 1 ? ",\n" : "\n");
        //     }
        }
        json.append("]");

        try {
            Path path = Paths.get(CAMINHO_JSON);
            Files.createDirectories(path.getParent());
            Files.writeString(path, json.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("[Erro ao salvar catalogo.json]: " + e.getMessage());
        }
    }

    private String extrairString(String texto, String chave) {
        Matcher m = Pattern.compile("\"" + chave + "\"\\s*:\\s*\"([^\"]+)\"").matcher(texto);
        return m.find() ? m.group(1) : "";
    }

    private double extrairNumero(String texto, String chave) {
        Matcher m = Pattern.compile("\"" + chave + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)").matcher(texto);
        return m.find() ? Double.parseDouble(m.group(1)) : 0.0;
    }
}