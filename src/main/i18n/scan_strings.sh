# /usr/bin/env bash

i18n_dir="src/main/i18n"
l10n_dir="src/main/l10n"
java_dir="src/main/java"
saddlebip_dir="${java_dir}/com/trufflecat/saddlebip"

lang_file="${i18n_dir}/langs"
po_template="${i18n_dir}/messages.pot"

shopt -s globstar # we like globstar

xgettext --output="${po_template}" \
         "${saddlebip_dir}"/**/*.java

while IFS= read -r l10n_lang; do
    po_file="${l10n_dir}/${l10n_lang}.po";
    # Remove the file if it already exists.
    test -f "${po_file}" && rm "${po_file}"
    msginit --no-translator \
            -l "${l10n_lang}" \
            -i "${po_template}" \
            -o "${po_file}"
done < <(cat "${lang_file}")