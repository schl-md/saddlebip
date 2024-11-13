# /usr/bin/env bash

i18n_dir="src/main/i18n"
l10n_dir="src/main/l10n"
resources_dir="src/main/resources"
lang_file="${i18n_dir}/langs"

while IFS= read -r l10n_lang; do
    po_file="${l10n_dir}/${l10n_lang}.po";
    properties_file="${resources_dir}/saddlebip_${l10n_lang}.properties"
    # Remove the file if it already exists.
    test -f "${properties_file}" && rm "${properties_file}"
    msgcat --properties-output \
           --output-file "${properties_file}" \
           "${po_file}"
done < <(cat "${lang_file}")