import React, {useEffect, useMemo, useState} from 'react';
import {FormLayout} from "@hilla/react-components/FormLayout";
import {TextField} from "@hilla/react-components/TextField";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {Upload} from "@hilla/react-components/Upload";
import {DatePicker} from "@hilla/react-components/DatePicker";
import {TextArea} from "@hilla/react-components/TextArea";
import {useForm} from "@hilla/react-form";
import { readAsDataURL } from "promise-file-reader";
import CreateListingRequestModel from "Frontend/generated/cz/asen/fridge/endpoint/request/CreateListingRequestModel";
import {EnumerationEndpoint, FoodListingEndpoint} from "Frontend/generated/endpoints";
import Allergens from "Frontend/generated/cz/asen/fridge/domain/enums/Allergens";
import {NavLink, useNavigate} from "react-router-dom";
import {Tab} from "@hilla/react-components/Tab";
import {Icon} from "@hilla/react-components/Icon";
import {Tabs} from "@hilla/react-components/Tabs";
import {MultiSelectComboBox} from "@hilla/react-components/MultiSelectComboBox";
import AllergensModel from "Frontend/generated/cz/asen/fridge/domain/enums/AllergensModel";

const iconStyle= "h-[var(--lumo-icon-size-s)] m-auto w-[var(--lumo-icon-size-s)]"

export default function FoodListingCreateView() {
    const navigate = useNavigate();

    const { model, submit, value,  field } = useForm(CreateListingRequestModel, {
        onSubmit: async (request) => {
            await FoodListingEndpoint.createListing(request)
            navigate("/food-listings")
        }
    })

    const [allergenItems, setAllergenItems] = useState<Allergens[]>(Object.values(AllergensModel));
    useEffect(() => {
        EnumerationEndpoint.getAllergens().then((allergens) => setAllergenItems(allergens));
    }, []);

    const responsiveSteps = [
        { minWidth: '0', columns: 1 },
        { minWidth: '500px', columns: 2 },
    ];

    return (
        <>
            <section className="flex justify-center items-start px-5 py-10 md:px-40 md:py-20 min-h-screen">
                <div className="max-w-4xl w-full">
                    <div className="mb-4 md:mb-8 flex items-center justify-between">
                        <h1 className="text-lg md:text-2xl font-semibold">Create listing</h1>
                    </div>
                    <VerticalLayout theme="spacing">
                        <FormLayout responsiveSteps={responsiveSteps}>
                            <TextField label="Listing name" {...field(model.shortDescription)}/>
                            <TextField label="Pickup location" {...field(model.pickupLocation)}>
                                <Icon slot="prefix" icon="vaadin:map-marker" />
                            </TextField>
                            <DatePicker label="Expiration date" {...field(model.expiryDate)}/>
                            <MultiSelectComboBox
                                label="Allergens"
                                items={allergenItems}
                            />
                            <TextArea {...{colspan: 2}} label="Description" {...field(model.description)}/>
                            <p>Images: </p>
                            <Upload capture="camera" accept="image/png" onUploadBefore={
                                async (e) => {
                                    const file = e.detail.file;
                                    e.preventDefault();
                                    if (value) {
                                        const base64Image = await readAsDataURL(file);
                                        if (value.base64Images) {
                                            value.base64Images.push(base64Image);
                                        } else {
                                            value.base64Images = [base64Image];
                                        }
                                    }
                                }
                            }/>
                        </FormLayout>
                    </VerticalLayout>
                </div>
            </section>
            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs" className="w-full bottom-0 lg:flex lg:justify-center lg:gap-6">
                <Tab aria-label="Back">
                    <NavLink to="/food-listings">
                        <Icon icon="vaadin:arrow-circle-left" className={iconStyle}/>
                    </NavLink>
                </Tab>
                <Tab aria-label="Create">
                    <NavLink to="#" onClick={submit}>
                        <Icon icon="vaadin:check-square" className={iconStyle}/>
                    </NavLink>
                </Tab>
            </Tabs>
        </>
    );
};