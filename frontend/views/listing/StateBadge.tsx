import React, {useEffect, useState} from "react";
import ClaimState from "Frontend/generated/cz/asen/fridge/domain/enums/ClaimState";
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

export default function StateBadge({state}: StateBadgeProps){
    const mapping: StateMapping[] = [
        {state: ClaimState.UNCLAIMED,   theme: 'badge contrast', icon: 'vaadin:ellipsis-dots-h',  text: 'Unclaimed'},
        {state: ClaimState.WAITING,     theme: 'badge',         icon: 'vaadin:clock',           text: 'Waiting for answer'},
        {state: ClaimState.ACCEPTED,    theme: 'badge success', icon: 'vaadin:time-forward',    text: 'Accepted'},
        {state: ClaimState.CLAIMED,     theme: 'badge success', icon: 'vaadin:check',           text: 'Claimed!'},
    ]

    const currentState = mapping.find(map => map.state === state) || mapping[0];

    return (
        <span {...{theme: currentState.theme}}>
            <Icon icon={currentState.icon} style={{padding: 'var(--lumo-space-xs)'}}/>
            <span>{currentState.text}</span>
        </span>
    );
}