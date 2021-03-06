/**
 * Copyright (c) Kestrel Technology LLC
 */
package net.certware.argument.gsz;

import net.certware.argument.arm.ReasoningElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Strategy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.certware.argument.gsz.Strategy#getGoals <em>Goals</em>}</li>
 *   <li>{@link net.certware.argument.gsz.Strategy#getJustifications <em>Justifications</em>}</li>
 *   <li>{@link net.certware.argument.gsz.Strategy#getStrategyContexts <em>Strategy Contexts</em>}</li>
 *   <li>{@link net.certware.argument.gsz.Strategy#getStrategySolutions <em>Strategy Solutions</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.certware.argument.gsz.GszPackage#getStrategy()
 * @model annotation="gmf.node label='identifier' label.pattern='Strategy {0}' label.icon='true' label.placement='internal' label.readonly='false' figure='net.certware.argument.diagram.figures.ParallelogramFigure' border.color='0,0,0' border.width='2' border.style='solid' size='100,60' color='250,250,250' tool.name='Strategy Node' tool.description='Creates a strategy node on the diagram canvas' tool.small.path='icons/obj16/parallelogram.png' tool.small.bundle='net.certware.core.ui' tool.large.path='icons/obj16/parallelogram.png' tool.large.bundle='net.certware.core.ui'"
 * @generated
 */
public interface Strategy extends ReasoningElement {

	/**
	 * Returns the value of the '<em><b>Goals</b></em>' reference list.
	 * The list contents are of type {@link net.certware.argument.gsz.Goal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goals</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goals</em>' reference list.
	 * @see net.certware.argument.gsz.GszPackage#getStrategy_Goals()
	 * @model annotation="gmf.link width='1' color='0,0,0' label='is solved by' target.decoration='filledclosedarrow' style='solid' tool.name='Strategy with Goal' tool.description='Links strategy node with goals invoked by the strategy' tool.small.path='icons/obj16/connector.png' tool.small.bundle='net.certware.core.ui' tool.large.path='icons/obj16/connector.png' tool.large.bundle='net.certware.core.ui'"
	 * @generated
	 */
	EList<Goal> getGoals();

	/**
	 * Returns the value of the '<em><b>Justifications</b></em>' reference list.
	 * The list contents are of type {@link net.certware.argument.gsz.Justification}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Justifications</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Justifications</em>' reference list.
	 * @see net.certware.argument.gsz.GszPackage#getStrategy_Justifications()
	 * @model annotation="gmf.link width='1' color='0,0,0' label='justification' target.decoration='closedarrow' style='solid' tool.name='Strategy with Justification' tool.description='Links strategy node with supporting justification' tool.small.path='icons/obj16/connector.png' tool.small.bundle='net.certware.core.ui' tool.large.path='icons/obj16/connector.png' tool.large.bundle='net.certware.core.ui'"
	 * @generated
	 */
	EList<Justification> getJustifications();

	/**
	 * Returns the value of the '<em><b>Strategy Contexts</b></em>' reference list.
	 * The list contents are of type {@link net.certware.argument.gsz.Context}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy Contexts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy Contexts</em>' reference list.
	 * @see net.certware.argument.gsz.GszPackage#getStrategy_StrategyContexts()
	 * @model annotation="gmf.link label='in context' width='1' color='0,0,0' target.decoration='closedarrow' style='solid' tool.name='Strategy with Context' tool.description='Links strategy node with supporting context' tool.small.path='icons/obj16/connector.png' tool.small.bundle='net.certware.core.ui' tool.large.path='icons/obj16/connector.png' tool.large.bundle='net.certware.core.ui'"
	 * @generated
	 */
	EList<Context> getStrategyContexts();

	/**
	 * Returns the value of the '<em><b>Strategy Solutions</b></em>' reference list.
	 * The list contents are of type {@link net.certware.argument.gsz.Solution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy Solutions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy Solutions</em>' reference list.
	 * @see net.certware.argument.gsz.GszPackage#getStrategy_StrategySolutions()
	 * @model annotation="gmf.link label='is solved by' width='1' color='0,0,0' target.decoration='filledclosedarrow' style='solid' tool.name='Strategy with Solution' tool.description='Links strategy node with supporting solution' tool.small.path='icons/obj16/connector.png' tool.small.bundle='net.certware.core.ui' tool.large.path='icons/obj16/connector.png' tool.large.bundle='net.certware.core.ui'"
	 * @generated
	 */
	EList<Solution> getStrategySolutions();

} // Strategy
