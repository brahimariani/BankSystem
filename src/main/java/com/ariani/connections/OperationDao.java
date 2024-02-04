package com.ariani.connections;

import com.ariani.dao.Compte;
import com.ariani.dao.Operation;

import java.util.List;

public interface OperationDao {
    boolean ajouter(Operation operation );
    List<Operation> lister(Compte compte);
    boolean updateAccount(Operation operation);
}
