package org.database.dao;

import org.domain.Professor;

public interface IProfessorDAO {
    int addProfessor(Professor profesor);
    int removeProfessor(Professor profesor);

}
