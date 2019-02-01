package com.gestordedatos.gestordedatos.pojos;

import com.gestordedatos.gestordedatos.Globals;

public class Bitacora {
    private int ID;
    private int class_element;
    private int ID_Element;
    private int operacion;

    public Bitacora() {
        this.setID(Globals.SIN_VALOR_INT);
        this.setClass_element(Globals.SIN_VALOR_INT);
        this.setID_Element(Globals.SIN_VALOR_INT);
        this.setOperacion(Globals.SIN_VALOR_INT);
    }

    public Bitacora(int ID, int ID_Element, int operacion) {
        this.setID(ID);
        this.setID_Element(ID_Element);
        this.setOperacion(operacion);
    }

    public int getClass_element() {
        return class_element;
    }

    public void setClass_element(final int class_element) {
        this.class_element = class_element;
    }

    public int getID_Element() {
        return ID_Element;
    }

    public void setID_Element(int ID_Element) {
        this.ID_Element = ID_Element;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
