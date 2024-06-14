import React from "react";
import ClaimState from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/ClaimState";
import {Icon} from "@hilla/react-components/Icon";

interface StateBadgeProps{
    state?: ClaimState
}

interface StateMapping {
    state: ClaimState,
    theme: string,
    icon: string,
    text: string
}

export default function StateBadge({state}: Readonly<StateBadgeProps>){
    const mapping: StateMapping[] = [
        {state: ClaimState.UNCLAIMED,    theme: 'badge contrast', icon: 'vaadin:ellipsis-dots-h',  text: 'Unclaimed'},
        {state: ClaimState.CLAIMED,      theme: 'badge',          icon: 'vaadin:clock',            text: 'Claimed'},
        {state: ClaimState.FINISHED,     theme: 'badge success',  icon: 'vaadin:check',            text: 'Finished'},
    ]

    const currentState = mapping.find(map => map.state === state) || mapping[0];

    return (
        <span {...{theme: currentState.theme}}>
            <Icon icon={currentState.icon} style={{padding: 'var(--lumo-space-xs)'}}/>
            <span>{currentState.text}</span>
        </span>
    );
}