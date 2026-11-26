package repository.dados;

import java.util.ArrayList;
import java.util.List;
import models.pedido.Pedido;

public class PedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Pedido buscarPorId(String id) {
        for (Pedido p : pedidos) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Pedido> listarPorLeitor(String leitorId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getLeitor().getId().equals(leitorId)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }
}