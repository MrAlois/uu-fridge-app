import React, {useEffect, useState} from 'react';
import {Dialog} from "@hilla/react-components/Dialog";
import FoodListing from "Frontend/generated/cz/asen/unicorn/fridge/domain/FoodListing";
import {Button} from "@hilla/react-components/Button";
import {useForm} from "@hilla/react-form";
import CreateListingModel from "Frontend/generated/cz/asen/unicorn/fridge/endpoint/operation/CreateListingModel";
import CreateListing from "Frontend/generated/cz/asen/unicorn/fridge/endpoint/operation/CreateListing";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {FormLayout} from "@hilla/react-components/FormLayout";
import {TextField} from "@hilla/react-components/TextField";
import {ComboBox} from "@hilla/react-components/ComboBox";
import {Icon} from "@hilla/react-components/Icon";
import {DatePicker} from "@hilla/react-components/DatePicker";
import {MultiSelectComboBox} from "@hilla/react-components/MultiSelectComboBox";
import {TextArea} from "@hilla/react-components/TextArea";
import {Upload} from "@hilla/react-components/Upload";
import {readAsDataURL} from "promise-file-reader";
import Allergen from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/Allergen";
import AllergenModel from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/AllergenModel";
import {EnumerationEndpoint, FoodListingEndpoint} from "Frontend/generated/endpoints";
import useLocationAutocomplete from "Frontend/hooks/useLocationAutocomplete";
import Location from "Frontend/generated/cz/asen/unicorn/fridge/endpoint/operation/CreateListing/Location";
import {Notification} from "@hilla/react-components/Notification";

type FoodListingEditProps = {
    listing?: FoodListing;
    isOpen: boolean,
    setIsOpen: (value: boolean) => void,
    onSubmit: (data: CreateListing) => void;
}

export default function FoodListingEditDialog({ listing, isOpen, setIsOpen, onSubmit }: Readonly<FoodListingEditProps>) {
    const { model, submit, value,  read, field } = useForm(CreateListingModel, {
        onSubmit: async (request) => {
            onSubmit(request);
            setIsOpen(false);
        }
    })

    useEffect(() => {
        if(listing?.id != null) {
            FoodListingEndpoint.getEditListingData(listing.id)
                .then(read)
                .catch(e => Notification.show(`Couldn't finds listings. ${JSON.stringify(e)}`, {
                    position: 'top-stretch',
                    theme: 'error',
                    duration: 4000,
                }));
        }
    }, [isOpen, listing])

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

    const footerSection = () => (
        <>
            <Button theme="secondary" onClick={() => setIsOpen(false)}>
                Cancel
            </Button>
            <Button theme="primary" onClick={submit}>
                {listing ? 'Edit' : 'Create'}
            </Button>
        </>
    );

    return (
        <Dialog
            theme="no-padding"
            header-title={listing ? 'Editing listing' : 'Creating listing'}
            opened={isOpen}
            onOpenedChanged={({ detail }) => {
                setIsOpen(detail.value);
            }}
            footerRenderer={footerSection}
        >
            <section className="flex justify-center items-start px-5 py-10 md:px-40 md:py-20 min-h-screen">
                <VerticalLayout className="max-w-4xl w-full" theme="spacing">
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
                            selectedItems={listing?.allergens}
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
            </section>
        </Dialog>
    );
};