/**
 * Copyright (c) 2011 Object Management Group (SAEM metamodel)
 * Copyright (c) 2010-2011 United States Government as represented by the Administrator for The National Aeronautics and Space Administration.  All Rights Reserved.  (generated models) 
 */

package net.certware.evidence.evidence;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Originality</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.certware.evidence.evidence.Originality#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.certware.evidence.evidence.EvidencePackage#getOriginality()
 * @model
 * @generated
 */
public interface Originality extends DocumentAttribute {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The literals are from the enumeration {@link net.certware.evidence.evidence.OriginalityLevel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see net.certware.evidence.evidence.OriginalityLevel
	 * @see #setValue(OriginalityLevel)
	 * @see net.certware.evidence.evidence.EvidencePackage#getOriginality_Value()
	 * @model
	 * @generated
	 */
	OriginalityLevel getValue();

	/**
	 * Sets the value of the '{@link net.certware.evidence.evidence.Originality#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see net.certware.evidence.evidence.OriginalityLevel
	 * @see #getValue()
	 * @generated
	 */
	void setValue(OriginalityLevel value);

} // Originality
