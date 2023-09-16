package br.com.mercadoalves.controle.domain;

import br.com.mercadoalves.controle.domain.cliente.AdmCliente;
import br.com.mercadoalves.controle.domain.cliente.Cliente;
import br.com.mercadoalves.controle.domain.cliente.ClienteService;
import br.com.mercadoalves.controle.domain.estoque.Estoque;
import br.com.mercadoalves.controle.domain.estoque.EstoqueService;
import br.com.mercadoalves.controle.domain.estoque.InformacoesCarrinho;
import br.com.mercadoalves.controle.domain.itensVenda.ItensVenda;
import br.com.mercadoalves.controle.domain.itensVenda.ItensVendaService;
import br.com.mercadoalves.controle.domain.usuario.Usuario;
import br.com.mercadoalves.controle.domain.usuario.UsuarioService;
import br.com.mercadoalves.controle.domain.venda.ComprasPorCpf;
import br.com.mercadoalves.controle.domain.venda.DadosVenda;
import br.com.mercadoalves.controle.domain.venda.InformacoesCupom;
import br.com.mercadoalves.controle.domain.venda.VendaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

public class ControleApplication {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            String requestURI = exchange.getRequestURI().toString();
            sendCORSHeaders(exchange);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");

            if ("OPTIONS".equals(requestMethod)) {
                sendCORSHeaders(exchange);
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
            } else if ("POST".equals(requestMethod)) {
                handlePostRequest(exchange);
            } else if ("GET".equals(requestMethod)) {
                handleGetRequest(exchange);
            } else if ("DELETE".equals(requestMethod)) {
                handleDeleteRequest(exchange);
            } else if ("PUT".equals(requestMethod)) {
                handlePutRequest(exchange);
            } else {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
            }
        }

        private void sendCORSHeaders(HttpExchange exchange) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().set("Access-Control-Max-Age", "86400");
        }

        private void handlePostRequest(HttpExchange exchange) throws IOException {
            sendCORSHeaders(exchange);
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            String action = getRequestAction(requestBody);
            String response = "";

            if ("novoItemCliente".equals(action)) {
                ClienteService service = new ClienteService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String cpf = jsonNode.get("cpf").asText();
                String nome = jsonNode.get("nome").asText();
                String nascimento = jsonNode.get("nascimento").asText();
                String telefone = jsonNode.get("telefone").asText();
                String email = jsonNode.get("email").asText();
                String endereco = jsonNode.get("endereco").asText();
                String cidade = jsonNode.get("cidade").asText();
                String estado = jsonNode.get("estado").asText();
                String cep = jsonNode.get("cep").asText();
                service.cadastrarCliente(cpf, nome, Date.valueOf(nascimento), telefone, email, endereco, cidade, estado, cep);
                response = "{\"status\": \"itemCadastrado\"}";
            }

            if ("novaVendaRealizada".equals(action)) {
                VendaService service = new VendaService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String numeroVenda = jsonNode.get("numeroVenda").asText();
                String cpf = jsonNode.get("cpf").asText();
                String valorTotalTexto = jsonNode.get("valorTotal").asText();
                String data = jsonNode.get("data").asText();
                String hora = jsonNode.get("hora").asText();
                String pagamento = jsonNode.get("pagamento").asText();
                String caixa = jsonNode.get("caixa").asText();
                BigDecimal valorTotal = new BigDecimal(valorTotalTexto);
                service.cadastrarVendaRealizada(numeroVenda, cpf, valorTotal, Date.valueOf(data), hora, pagamento, caixa);
                response = "{\"status\": \"VendaCadastrada\"}";
            }

            if ("novoItensVenda".equals(action)) {
                ItensVendaService service = new ItensVendaService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String numeroVenda = jsonNode.get("numeroVenda").asText();
                JsonNode itensArray = jsonNode.get("itens");
                String quantidade = itensArray.get("quantidade").asText();
                String codProduto = itensArray.get("codigo").asText();
                String valorTexto = itensArray.get("valor").asText();
                String cupom = itensArray.get("cupom").asText();
                BigDecimal valor = new BigDecimal(valorTexto);
                service.novoItensVenda(quantidade, codProduto, valor, cupom, numeroVenda);
                response = "{\"status\": \"vendaFinalizada\"}";
                }

            if ("novoItemEstoque".equals(action)) {
                EstoqueService service = new EstoqueService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String codigo = jsonNode.get("codigo").asText();
                String produto = jsonNode.get("produto").asText();
                String lote = jsonNode.get("lote").asText();
                String quantidadeTexto = jsonNode.get("quantidade").asText();
                String valorTexto = jsonNode.get("valor").asText();
                String vencimento = jsonNode.get("vencimento").asText();
                String status = jsonNode.get("status").asText();
                String foto = jsonNode.get("foto").asText();
                ;
                Double quantidade = new Double(quantidadeTexto);
                BigDecimal valor = new BigDecimal(valorTexto);
                service.cadastrarEstoque(codigo, produto, lote, quantidade, valor, Date.valueOf(vencimento), status, foto);
                response = "{\"status\": \"itemCadastrado\"}";
            }

            if ("novoItemFuncionario".equals(action)) {
                UsuarioService service = new UsuarioService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String id = jsonNode.get("id").asText();
                String nome = jsonNode.get("nome").asText();
                String cargo = jsonNode.get("cargo").asText();
                String login = jsonNode.get("login").asText();
                String senha = jsonNode.get("senha").asText();
                String acesso = jsonNode.get("acesso").asText();
                service.cadastrarUsuario(id, nome, cargo, login, senha, acesso);
                response = "{\"status\": \"itemCadastrado\"}";
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }

        private void handleGetRequest(HttpExchange exchange) throws IOException {
            sendCORSHeaders(exchange);
            String requestURI = exchange.getRequestURI().toString();
            String action = getRequestParamValue(requestURI, "action");
            String parametro1 = getRequestParamValue(requestURI, "parametro1");
            String codProduto = getRequestParamValue(requestURI, "codProduto");
            String cpf = getRequestParamValue(requestURI, "cpf");
            String coluna = getRequestParamValue(requestURI, "coluna");
            String ordem = getRequestParamValue(requestURI, "ordem");
            String cupom = getRequestParamValue(requestURI, "cupom");
            String quantidade = getRequestParamValue(requestURI, "quantidade");
            String subtotalStr = getRequestParamValue(requestURI, "subtotal");

            int item = 0;
            boolean itemExistente = false;

            if ("verificaAcesso".equals(action)) {
                String login = getRequestParamValue(requestURI, "usuario");
                String senha = getRequestParamValue(requestURI, "senha");
                String response = verificaNivelAcesso(login, senha);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("informacoesComprasCPF".equals(action)) {
                String response = listarComprasPorCpf(coluna, ordem, cpf);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("informacoesGeraisCupom".equals(action)) {
                String response = informacoesGeraisCupom(cupom);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("localizaCupom".equals(action)) {
                String response = listarItensVendas(cupom);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("atualizaTabelaVenda".equals(action)) {
                String listaCarrinho = listaItensVenda(codProduto, quantidade, subtotalStr);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = listaCarrinho.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(listaCarrinho.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("atualizaClientes".equals(action)) {
                String response = listarClientes(coluna, ordem);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("localizaCliente".equals(action)) {
                String cpfCliente = getRequestParamValue(requestURI, "cpf");
                String response = listarClientePorCpf(cpfCliente);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("localizaEstoque".equals(action)) {
                String codigo = getRequestParamValue(requestURI, "codProduto");
                String response = listarEstoquePorCodigo(codigo);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("localizaProdutoVenda".equals(action)) {
                String codigo = getRequestParamValue(requestURI, "codProduto");
                String response = listarEstoquePorCodigo(codigo);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("localizaFuncionario".equals(action)) {
                String id = getRequestParamValue(requestURI, "parametro1");
                String response = listarUsuarioPorId(id);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("atualizaEstoque".equals(action)) {
                String response = listarEstoque(coluna, ordem);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("atualizaFuncionarios".equals(action)) {
                String response = listarUsuarios(coluna, ordem);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("atualizaVendas".equals(action)) {
                String response = listarVendas(coluna, ordem);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }
        }
        private Estoque parseResponseIntoObject(String response) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Estoque estoque = objectMapper.readValue(response, Estoque.class);
                return estoque;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        private String getRequestParamValue(String requestURI, String paramName) {
            String[] parts = requestURI.split("\\?");
            if (parts.length > 1) {
                String query = parts[1];
                String[] queryParams = query.split("&");
                for (String param : queryParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2 && paramName.equals(keyValue[0])) {
                        return keyValue[1];
                    }
                }
            }
            return null;
        }
        private void handleDeleteRequest(HttpExchange exchange) throws IOException {
            sendCORSHeaders(exchange);
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String action = getRequestAction(requestBody);

            if ("apagaItemCompra".equals(action)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String itensApagar = jsonNode.get("apagar").asText();

                String response = "{\"message\": \"item apagado\"}";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            } else if ("apagaItemCliente".equals(action)) {
                ClienteService service = new ClienteService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String cpf = jsonNode.get("itensApagar").asText();

                service.apagarCliente(cpf);
                String response = "{\"message\": \"item apagado\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            } else if ("apagaItemEstoque".equals(action)) {
                EstoqueService service = new EstoqueService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String codigo = jsonNode.get("itensApagar").asText();
                service.apagarEstoque(codigo);
                String response = "{\"message\": \"item apagado\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            } else if ("apagaItemFuncionario".equals(action)) {
                UsuarioService service = new UsuarioService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String id = jsonNode.get("itensApagar").asText();
                service.apagarUsuario(id);

                String response = "{\"message\": \"item apagado\"}";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            } else {
                String response = "{\"message\": \"DELETE request handled\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }
        }

        private void handlePutRequest(HttpExchange exchange) throws IOException {
            sendCORSHeaders(exchange);
            String requestURI = exchange.getRequestURI().toString();
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String action = getRequestAction(requestBody);

            if ("modificaCliente".equals(action)) {
                ClienteService service = new ClienteService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String nome = jsonNode.get("nome").asText();
                String nascimento = jsonNode.get("nascimento").asText();
                String cpf = jsonNode.get("cpf").asText();
                String telefone = jsonNode.get("telefone").asText();
                String email = jsonNode.get("email").asText();
                String endereco = jsonNode.get("endereco").asText();
                String cidade = jsonNode.get("cidade").asText();
                String estado = jsonNode.get("estado").asText();
                String cep = jsonNode.get("cep").asText();
                service.alterarCliente(nome, Date.valueOf(nascimento), cpf, telefone, email, endereco, cidade, estado, cep);
                String response = "{\"message\": \"modificado\"}";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("modificaFuncionario".equals(action)) {
                UsuarioService service = new UsuarioService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String id = jsonNode.get("id").asText();
                String nome = jsonNode.get("nome").asText();
                String cargo = jsonNode.get("cargo").asText();
                String login = jsonNode.get("login").asText();
                String senha = jsonNode.get("senha").asText();
                String acesso = jsonNode.get("acesso").asText();
                service.alterarUsuario(id, nome, cargo, login, senha, acesso);
                String response = "{\"message\": \"modificado\"}";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("modificaEstoque".equals(action)) {
                EstoqueService service = new EstoqueService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String codigo = jsonNode.get("codigo").asText();
                String produto = jsonNode.get("produto").asText();
                String lote = jsonNode.get("lote").asText();
                String quantidadeTexto = jsonNode.get("quantidade").asText();
                String valorTexto = jsonNode.get("valor").asText();
                String vencimento = jsonNode.get("vencimento").asText();
                String status = jsonNode.get("status").asText();
                String foto = jsonNode.get("foto").asText();
                Double quantidade = new Double(quantidadeTexto);
                BigDecimal valor = new BigDecimal(valorTexto);
                service.alterarEstoque(codigo, produto, lote, quantidade, valor, Date.valueOf(vencimento), status, foto);
                String response = "{\"message\": \"modificado\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }

            if ("atualizaQuantidadeEstoque".equals(action)) {
                EstoqueService service = new EstoqueService();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String codigo = jsonNode.get("codigo").asText();
                String quantidadeTexto = jsonNode.get("quantidade").asText();
                Double quantidade = new Double(quantidadeTexto);
                service.alterarQuantidadeEstoque(codigo, quantidade);
                String response = "{\"message\": \"modificado\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }
        }

        private String getRequestAction(String requestBody) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                JsonNode actionNode = jsonNode.get("action");

                if (actionNode != null && actionNode.isTextual()) {
                    return actionNode.asText();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String listarClientes(String coluna, String ordem) {
            ClienteService service = new ClienteService();
            List<AdmCliente> informacoesCliente = service.listarClientes(coluna, ordem);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesCliente);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarUsuarios(String coluna, String ordem) {
            UsuarioService service = new UsuarioService();
            List<Usuario> informacoesUsuario = service.listarUsuarios(coluna, ordem);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesUsuario);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarEstoque(String coluna, String ordem) {
            EstoqueService service = new EstoqueService();
            List<Estoque> informacoesEstoque = service.listarEstoque(coluna, ordem);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesEstoque);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarVendas(String coluna, String ordem) {
            VendaService service = new VendaService();
            List<DadosVenda> informacoesVenda = service.listarVendas(coluna, ordem);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesVenda);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarItensVendas(String cupom) {
            ItensVendaService service = new ItensVendaService();
            List<ItensVenda> informacoesItensVenda = service.listarItensVendas(cupom);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesItensVenda);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarComprasPorCpf(String coluna, String ordem, String cpf) {
            VendaService service = new VendaService();
            List<ComprasPorCpf> informacoesVendaPorCpf = service.listarComprasPorCpf(coluna, ordem, cpf);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesVendaPorCpf);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarUsuarioPorId(String id) {
            UsuarioService service = new UsuarioService();
            Usuario informacoesUsuario = service.listarUsuarioPorId(id);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesUsuario);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String verificaNivelAcesso(String usuario, String senha) {
            UsuarioService service = new UsuarioService();
            Usuario informacoesUsuario = service.verificaNivelAcesso(usuario, senha);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesUsuario.getAcesso());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarEstoquePorCodigo(String codigo) {
            EstoqueService service = new EstoqueService();
            Estoque informacoesEstoque = service.listarEstoquePorCodigo(codigo);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesEstoque);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listaItensVenda(String codigo, String quantidadeStr, String subtotalStr) {
            EstoqueService service = new EstoqueService();
            Estoque informacoesEstoque = service.listarEstoquePorCodigo(codigo);
            String descricao = informacoesEstoque.getProduto();
            String codProduto = informacoesEstoque.getCodigo();
            BigDecimal valor = informacoesEstoque.getValor();

            BigDecimal quantidade = new BigDecimal(quantidadeStr);
            BigDecimal valorTotal = valor.multiply(quantidade);

            BigDecimal currentSubtotal = new BigDecimal(subtotalStr);
            BigDecimal subtotal = currentSubtotal.add(valorTotal);

            InformacoesCarrinho informacoesCarrinho = new InformacoesCarrinho(descricao, codProduto, quantidade, valor, valorTotal, subtotal);
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return objectMapper.writeValueAsString(informacoesCarrinho);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private String listarClientePorCpf(String cpf) {
            ClienteService service = new ClienteService();
            Cliente informacoesCliente = service.listarClientePorCpf(cpf);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesCliente);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        private static void sleep(long milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private String informacoesGeraisCupom(String cupom) {
            VendaService service = new VendaService();
            InformacoesCupom informacoesGeraisCupom = service.informacoesGeraisCupom(cupom);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(informacoesGeraisCupom);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}

