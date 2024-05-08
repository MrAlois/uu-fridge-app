import { AppLayout, type AppLayoutElement } from '@hilla/react-components/AppLayout.js';
import React, {ReactNode, useEffect, useRef, useState} from 'react';
import {Tab} from "@hilla/react-components/Tab";
import {Tabs} from "@hilla/react-components/Tabs";
import {Icon} from "@hilla/react-components/Icon";
import {MenuBar} from "@hilla/react-components/MenuBar";
import {HorizontalLayout} from "@hilla/react-components/HorizontalLayout";
import {NavLink} from "react-router-dom";
import {ConfirmDialog} from "@hilla/react-components/ConfirmDialog";
import {Button} from "@hilla/react-components/Button";


const headerStyle = {
    fontSize: 'var(--lumo-font-size-l)',
    margin: 'var(--lumo-space-m) var(--lumo-space-l)',
    width: "100%"
};

const iconStyle = {
    height: 'var(--lumo-icon-size-s)',
    margin: 'auto',
    width: 'var(--lumo-icon-size-s)',
};

const tabsStyle = {
    width: '100%',
    bottom: 0
};

type MainLayoutProps = {
    children: ReactNode;
};

export default function MainLayout() {
    const appLayoutRef = useRef<AppLayoutElement>(null);
    const [dialogOpened, setDialogOpened] = useState(false);

    useEffect(() => {
        const appLayout = appLayoutRef.current;
        if (appLayout) {
            // --vaadin-app-layout-touch-optimized is only enforced as part of this example
            appLayout.style.setProperty('--vaadin-app-layout-touch-optimized', 'true');
            (appLayout as any)._updateTouchOptimizedMode();
        }
    }, []);

    return (
        <AppLayout ref={appLayoutRef}>
            <header slot="navbar" style={headerStyle}>
                <HorizontalLayout>
                    Food Rescue Fridge App
                </HorizontalLayout>
            </header>

            <ConfirmDialog
                header="Are you sure?"
                confirmText="Cancel"
                opened={dialogOpened}
                onOpenedChanged={(event) => setDialogOpened(event.detail.value)}
                onConfirm={() => {
                    console.log("Order Cancelled")
                }}
                onReject={() => {
                    console.log("Discared")
                }}
            >
                By cancelling you won't be able to do stuff.
            </ConfirmDialog>

            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs" style={tabsStyle}>
                <Tab aria-label="Back">
                    <NavLink to="/food-listings" tabIndex={-1}>
                        <Icon icon="vaadin:arrow-circle-left" style={iconStyle}/>
                    </NavLink>
                </Tab>
                <Tab aria-label="Create">
                    <NavLink to="/add-listing" tabIndex={-1}>
                        <Icon icon="vaadin:plus-circle" style={iconStyle}/>
                    </NavLink>
                </Tab>
                <Tab aria-label="Cancel">
                    <a onClick={() => setDialogOpened(true)} tabIndex={-1}>
                        <Icon icon="vaadin:close-circle" style={iconStyle}/>
                    </a>
                </Tab>
            </Tabs>
        </AppLayout>
    );
};

