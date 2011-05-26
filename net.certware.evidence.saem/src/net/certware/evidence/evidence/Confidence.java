/**
 * Copyright (c) 2011 Object Management Group (SAEM metamodel)
 * Copyright (c) 2011 National Aeronautics and Space Administration.  All rights reserved. (generated models) 
 */
package net.certware.evidence.evidence;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Confidence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.certware.evidence.evidence.Confidence#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.certware.evidence.evidence.EvidencePackage#getConfidence()
 * @model
 * @generated
 */
public interface Confidence extends EvidenceAttribute {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The literals are from the enumeration {@link net.certware.evidence.evidence.ConfidenceLevel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see net.certware.evidence.evidence.ConfidenceLevel
	 * @see #setValue(ConfidenceLevel)
	 * @see net.certware.evidence.evidence.EvidencePackage#getConfidence_Value()
	 * @model
	 * @generated
	 */
	ConfidenceLevel getValue();

	/**
	 * Sets the value of the '{@link net.certware.evidence.evidence.Confidence#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see net.certware.evidence.evidence.ConfidenceLevel
	 * @see #getValue()
	 * @generated
	 */
	void setValue(ConfidenceLevel value);

} // Confidence
