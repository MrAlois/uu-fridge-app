import React, {useContext, useEffect, useState} from 'react';
import {FormLayout} from "@hilla/react-components/FormLayout";
import {TextField} from "@hilla/react-components/TextField";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {Upload} from "@hilla/react-components/Upload";
import {DatePicker} from "@hilla/react-components/DatePicker";
import {TextArea} from "@hilla/react-components/TextArea";
import {useForm} from "@hilla/react-form";
import {readAsDataURL} from "promise-file-reader";
import {EnumerationEndpoint, FoodListingEndpoint} from "Frontend/generated/endpoints";
import {NavLink, useNavigate} from "react-router-dom";
import {Tab} from "@hilla/react-components/Tab";
import {Icon} from "@hilla/react-components/Icon";
import {Tabs} from "@hilla/react-components/Tabs";
import {MultiSelectComboBox} from "@hilla/react-components/MultiSelectComboBox";
import {ComboBox} from "@hilla/react-components/ComboBox";
import useLocationAutocomplete from "Frontend/hooks/useLocationAutocomplete";
import CreateListingModel from "Frontend/generated/cz/asen/unicorn/fridge/endpoint/operation/CreateListingModel";
import Allergen from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/Allergen";
import AllergenModel from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/AllergenModel";
import Location from "Frontend/generated/cz/asen/unicorn/fridge/endpoint/operation/CreateListing/Location";
import {Tooltip} from "@hilla/react-components/Tooltip";
import {UserContext} from "Frontend/components/UserProvider";

const iconStyle= "h-[var(--lumo-icon-size-s)] m-auto w-[var(--lumo-icon-size-s)]"

export default function FoodListingCreateView() {
    const navigate = useNavigate();
    const { currentUser } = useContext(UserContext)

    const { model, submit, value,  field } = useForm(CreateListingModel, {
        onSubmit: async (request) => {
            request.donor = currentUser;
            await FoodListingEndpoint.createFoodListing(request).catch(e => alert(`Unhandled exception! ${JSON.stringify(e)}`))
            navigate("/food-listings")
        }
    })

    const [allergenItems, setAllergenItems] = useState<Allergen[]>(Object.values(AllergenModel));
    useEffect(() => {
        EnumerationEndpoint.getAllergens()
            .then((allergens) => setAllergenItems(allergens))
            .catch(e => alert(`Unhandled exception! ${JSON.stringify(e)}`));
    }, []);

    const [locationQuery, setLocationQuery] = useState<string>('');
    const locationOptions = useLocationAutocomplete(locationQuery)
        .map((option): Location => {
            return {
                locationName: String(option.display_name),
                latitude: Number(option.lat) || 0.0,
                longitude: Number(option.lon) || 0.0
            };
        });

    const [selectedLocation, setSelectedLocation] = useState<Location | undefined>();
    const handleFilterChange = (e: any) => setLocationQuery(e.detail.value);
    const handleValueChange = (e: any) => {
        const selectedOption = locationOptions.find(option => option.locationName === e.detail.value);
        setSelectedLocation(selectedOption);
    };

    return (
        <>
            <section className="flex justify-center items-start px-5 py-10 md:px-40 md:py-20 min-h-screen">
                <div className="max-w-4xl w-full">
                    <div className="mb-4 md:mb-8 flex items-center justify-between">
                        <h1 className="text-lg md:text-2xl font-semibold">Create listing</h1>
                    </div>
                    <VerticalLayout theme="spacing">
                        <FormLayout responsiveSteps={[
                            { minWidth: '0', columns: 1 },
                            { minWidth: '500px', columns: 2 },
                        ]}>
                            <TextField label="Listing name" {...field(model.shortDescription)}/>
                            <ComboBox
                                allowCustomValue
                                label="Pickup location"
                                onFilterChanged={handleFilterChange}
                                onValueChanged={handleValueChange}
                                items={locationOptions}
                                itemLabelPath="locationName"
                                {...field(model.pickupLocation)}
                            >
                                <Icon slot="prefix" icon="vaadin:map-marker" />
                            </ComboBox>
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
                    <Tooltip slot="tooltip" text="Back" position="top" />
                </Tab>
                <Tab aria-label="Create">
                    <NavLink to="#" onClick={submit}>
                        <Icon icon="vaadin:check-square" className={iconStyle}/>
                    </NavLink>
                    <Tooltip slot="tooltip" text="Create listing" position="top" />
                </Tab>
            </Tabs>
        </>
    );
};