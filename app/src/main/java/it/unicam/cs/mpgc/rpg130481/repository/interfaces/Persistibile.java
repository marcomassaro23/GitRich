package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

/**
 * Interfaccia che rappresenta un repository persistibile.
 * Estende le interfacce Caricabile e Salvabile per consentire il caricamento e il salvataggio dei dati.
 *
 * @param <T> Il tipo di dati gestiti dal repository.
 */
public interface Persistibile<T> extends Caricabile<T>, Salvabile<T> {}
